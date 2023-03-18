package com.sosyal.api.routes

import com.sosyal.api.data.dto.PostDto
import com.sosyal.api.data.dto.response.BaseResponse
import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.util.Connection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.koin.ktor.ext.inject

fun Route.configurePostRoutes(connections: MutableSet<Connection>) {
    val postRepository by inject<PostRepository>()

    authenticate("auth-jwt") {
        webSocket("/post") {
            val principal = call.principal<JWTPrincipal>()
            val postEdit = call.request.queryParameters["postEdit"]
            val username = principal!!.payload.getClaim("username").asString()
            val connection = Connection(
                session = this,
                username = username
            )
            connections += connection

            try {
                postRepository.getAllPosts().forEach { postDto ->
                    connection.session.send(Json.encodeToString(postDto))
                }

                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val postDtoText = frame.readText()
                    val postDto = Json.decodeFromString<PostDto>(postDtoText)

                    val id = if (postEdit == "true") {
                        postRepository.editPost(
                            id = postDto.id!!,
                            postDto = postDto
                        )
                    } else {
                        postRepository.addPost(postDto)
                    }

                    connections.forEach { conn ->
                        conn.session.send(Json.encodeToString(postDto.copy(id = id)))
                    }
                }


            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }

        delete("/posts/{id?}") {
            val id = call.parameters["id"].toString()

            val result = postRepository.deletePost(id)

            if (!result) {
                return@delete call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = BaseResponse<JsonObject>(
                        message = "Cannot delete the post"
                    )
                )
            }

            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse<JsonObject>(
                    message = "Post has been deleted successfully"
                )
            )
        }
    }
}