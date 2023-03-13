package com.sosyal.api.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sosyal.api.model.dto.LoginRequest
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.configureAuthRoutes(
    secret: String,
    issuer: String,
    audience: String
) {
    post("/login") {
        val loginRequest = call.receive<LoginRequest>()

        if (loginRequest.username != "test" || loginRequest.password != "123") {
            call.respond("Username or password is incorrect")
        }

        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", loginRequest.username)
            .sign(Algorithm.HMAC256(secret))

        call.respond(hashMapOf("token" to token))
    }
}
