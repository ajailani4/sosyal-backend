package com.sosyal.api.routes

import com.sosyal.api.data.dto.ChatDto
import com.sosyal.api.data.dto.response.BaseResponse
import com.sosyal.api.data.repository.ChatRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import org.koin.ktor.ext.inject

fun Route.configureChatRoutes() {
    val chatRepository by inject<ChatRepository>()

    authenticate("auth-jwt") {
        route("/chats") {
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
                        message = "Post has been deleted successfully"
                    )
                )
            }
        }
    }
}