package com.sosyal.api.routes

import com.sosyal.api.data.dto.CommentDto
import com.sosyal.api.data.dto.response.BaseResponse
import com.sosyal.api.data.repository.CommentRepository
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.util.CommentConnection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.inject

fun Route.configureCommentRoutes(commentConnections: MutableSet<CommentConnection>) {
    val commentRepository by inject<CommentRepository>()
    val userRepository by inject<UserRepository>()

    authenticate("auth-jwt") {
        webSocket("/comment") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim("username").asString()
            val postId = call.request.queryParameters["postId"].toString()
            val commentConnection = CommentConnection(
                session = this,
                username = username,
                postId = postId
            )
            commentConnections += commentConnection

            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val commentDtoText = frame.readText()
                    var commentDto = Json.decodeFromString<CommentDto>(commentDtoText)
                    val userDto = userRepository.getUser(commentDto.username)
                    commentDto = commentDto.copy(userAvatar = userDto?.avatar)

                    val id = commentRepository.addComment(commentDto)

                    commentConnections.filter { it.postId == postId }.forEach { conn ->
                        conn.session.send(Json.encodeToString(commentDto.copy(id = id)))
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Remove $commentConnection")
                commentConnections -= commentConnection
            }
        }

        get("/comments/{postId}") {
            val postId = call.parameters["postId"].toString()

            val comments = commentRepository.getCommentsByPostId(postId)

            call.respond(
                status = HttpStatusCode.OK,
                message = BaseResponse(
                    message = "Comments have been retrieved successfully",
                    data = comments
                )
            )
        }
    }
}