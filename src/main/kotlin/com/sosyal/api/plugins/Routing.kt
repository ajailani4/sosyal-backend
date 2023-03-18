package com.sosyal.api.plugins

import com.sosyal.api.routes.configureAuthRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()

    routing {
        configureAuthRoutes(
            secret = secret,
            issuer = issuer,
            audience = audience
        )
    }
}
