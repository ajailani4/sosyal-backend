package com.sosyal.api.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sosyal.api.data.model.request.RegisterRequest
import com.sosyal.api.data.model.response.BaseResponse
import com.sosyal.api.data.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.JsonObject
import org.koin.ktor.ext.inject

fun Route.configureAuthRoutes(
    secret: String,
    issuer: String,
    audience: String
) {
   val userRepository by inject<UserRepository>()

    post("/register") {
        val registerRequest = call.receive<RegisterRequest>()

        if (userRepository.getUser(registerRequest.username) != null) {
            return@post call.respond(
                status = HttpStatusCode.Conflict,
                message = BaseResponse<JsonObject>(
                    message = "Username already exists"
                )
            )
        }

        val result = userRepository.addUser(registerRequest)

        if (!result) {
            return@post call.respond(
                status = HttpStatusCode.BadRequest,
                message = BaseResponse<JsonObject>(
                    message = "Register account is failed"
                )
            )
        }

        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", registerRequest.username)
            .sign(Algorithm.HMAC256(secret))

        call.respond(
            status = HttpStatusCode.Created,
            message = BaseResponse(
                message = "Register account is success",
                data = hashMapOf("accessToken" to token)
            )
        )
    }
}
