package com.sosyal.api.routes

import com.sosyal.api.data.dto.response.BaseResponse
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.util.CloudinaryUtil
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import org.koin.ktor.ext.inject

fun Route.configureUserRoutes() {
    val userRepository by inject<UserRepository>()

    authenticate("auth-jwt") {
        route("/profile") {
            get {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val userDto = userRepository.getUser(username) ?: return@get call.respond(
                    status = HttpStatusCode.NotFound,
                    message = BaseResponse<JsonObject>(
                        message = "User is not found"
                    )
                )

                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse(
                        message = "User has been retrieved successfully",
                        data = userDto.copy(password = null)
                    )
                )
            }

            put("{id?}") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val multipartData = call.receiveMultipart()

                var name = ""
                var email = ""
                var avatar: String? = null

                multipartData.forEachPart { part ->
                    when (part) {
                        is PartData.FormItem -> {
                            when (part.name) {
                                "name" -> name = part.value
                                "email" -> email = part.value
                            }
                        }

                        is PartData.FileItem -> {
                            when (part.name) {
                                "avatar" -> {
                                    val fileBytes = part.streamProvider().readBytes()
                                    avatar = CloudinaryUtil.uploadImage(fileBytes)["url"].toString()
                                }
                            }
                        }

                        else -> {}
                    }

                    part.dispose()
                }

                val result = userRepository.editUser(
                    username = username,
                    name = name,
                    email = email,
                    avatar = avatar
                )

                if (!result) {
                    return@put call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = BaseResponse<JsonObject>(
                            message = "Cannot edit the profile"
                        )
                    )
                }

                call.respond(
                    status = HttpStatusCode.OK,
                    message = BaseResponse<JsonObject>(
                        message = "Profile has been edited"
                    )
                )
            }
        }
    }
}