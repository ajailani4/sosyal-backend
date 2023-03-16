package com.sosyal.api.routes

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sosyal.api.data.model.request.LoginRequest
import com.sosyal.api.data.model.request.RegisterRequest
import com.sosyal.api.data.model.response.BaseResponse
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.util.JWTUtil
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

        val token = JWTUtil.createJWT(
            secret = secret,
            issuer = issuer,
            audience = audience,
            username = registerRequest.username
        )

        call.respond(
            status = HttpStatusCode.Created,
            message = BaseResponse(
                message = "Register account is success",
                data = hashMapOf("accessToken" to token)
            )
        )
    }

    post("/login") {
        val loginRequest = call.receive<LoginRequest>()

        val user = userRepository.getUser(loginRequest.username) ?: return@post call.respond(
            status = HttpStatusCode.Unauthorized,
            message = BaseResponse<JsonObject>(
                message = "Username or password is incorrect"
            )
        )

        val pwComparingResult = BCrypt.verifyer().verify(
            loginRequest.password.toCharArray(),
            user.password
        )

        if (!pwComparingResult.verified) {
            return@post call.respond(
                status = HttpStatusCode.Unauthorized,
                message = BaseResponse<JsonObject>(
                    message = "Username or password is incorrect"
                )
            )
        }

        val token = JWTUtil.createJWT(
            secret = secret,
            issuer = issuer,
            audience = audience,
            username = loginRequest.username
        )

        call.respond(
            status = HttpStatusCode.Created,
            message = BaseResponse(
                message = "Login account is success",
                data = hashMapOf("accessToken" to token)
            )
        )
    }
}
