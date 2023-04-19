package com.sosyal.api.routes

import com.sosyal.api.data.dto.CommentDto
import com.sosyal.api.data.dto.response.BaseResponse
import com.sosyal.api.data.repository.CommentRepository
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.util.CommentConnection
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
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
                val comments = commentRepository.getCommentsByPostId(postId)

                if (comments.isNotEmpty()) {
                    comments.forEach { commentDto ->
                        commentConnection.session.send(Json.encodeToString(commentDto))
                    }
                } else {
                    commentConnection.session.send(
                        Json.encodeToString(
                            CommentDto(
                                postId = "",
                                username = "",
                                content = ""
                            )
                        )
                    )
                }

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
    }
}