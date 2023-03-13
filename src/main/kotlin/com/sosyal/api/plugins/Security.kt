package com.sosyal.api.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.application.*

fun Application.configureSecurity() {
    val jwtSecret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT.require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )
            validate {
                val username = it.payload.getClaim("username").asString()

                if (username == "test") {
                    JWTPrincipal(it.payload)
                } else {
                    null
                }
            }
        }
    }
}
