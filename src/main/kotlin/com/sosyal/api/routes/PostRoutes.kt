package com.sosyal.api.routes

import com.sosyal.api.data.dto.PostDto
import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.util.Connection
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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

                    val result = if (postEdit == "true") {
                        postRepository.editPost(
                            id = postDto.id!!,
                            postDto = postDto
                        )
                    } else {
                        postRepository.addPost(postDto)
                    }

                    if (result) {
                        connections.forEach { conn ->
                            conn.session.send(Json.encodeToString(postDto))
                        }
                    }
                }


            } catch (e: Exception) {
                println(e.localizedMessage)
            }
        }
    }
}