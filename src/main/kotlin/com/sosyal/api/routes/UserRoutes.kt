package com.sosyal.api.routes

import com.sosyal.api.data.dto.response.BaseResponse
import com.sosyal.api.data.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
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
        }
    }
}