package com.sosyal.api.routes

import com.sosyal.api.data.dto.ChatDto
import com.sosyal.api.data.dto.MessageDto
import com.sosyal.api.data.dto.response.BaseResponse
import com.sosyal.api.data.repository.ChatRepository
import com.sosyal.api.util.ChatConnection
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.koin.ktor.ext.inject

fun Route.configureChatRoutes(chatConnections: MutableSet<ChatConnection>) {
    val chatRepository by inject<ChatRepository>()

    authenticate("auth-jwt") {
        webSocket("/chat") {
            val principal = call.principal<JWTPrincipal>()
            val username = principal!!.payload.getClaim("username").asString()
            val chatId = call.request.queryParameters["chatId"].toString()
            val chatConnection = ChatConnection(
                session = this,
                chatId = chatId,
                username = username
            )
            chatConnections += chatConnection

            try {
                val messages = chatRepository.getMessagesByChatId(chatId)

                if (messages.isNotEmpty()) {
                    messages.forEach { messageDto ->
                        chatConnection.session.send(Json.encodeToString(messageDto))
                    }
                } else {
                    chatConnection.session.send(
                        Json.encodeToString(
                            MessageDto(
                                chatId = "",
                                username = "",
                                content = ""
                            )
                        )
                    )
                }

                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val messageDtoText = frame.readText()
                    val messageDto = Json.decodeFromString<MessageDto>(messageDtoText)

                    val id = chatRepository.addMessage(messageDto)

                    chatConnections.filter { it.chatId == chatId }.forEach { conn ->
                        conn.session.send(Json.encodeToString(messageDto.copy(id = id)))
                    }
                }
            } catch (e: Exception) {
                println(e.localizedMessage)
            } finally {
                println("Remove $chatConnection")
                chatConnections -= chatConnection
            }
        }

        route("/chats") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val chatsDto = chatRepository.getChatsByParticipants(username)

                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(
                        message = "Chats have been retrieved successfully",
                        data = chatsDto
                    )
                )
            }

            post {
                val chatDto = call.receive<ChatDto>()
                val isSuccess = chatRepository.createChat(chatDto)

                if (!isSuccess) {
                    return@post call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = BaseResponse<JsonObject>(
                            message = "Cannot create a chat"
                        )
                    )
                }

                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse<JsonObject>(
                        message = "Chat has been created successfully"
                    )
                )
            }
        }
    }
}