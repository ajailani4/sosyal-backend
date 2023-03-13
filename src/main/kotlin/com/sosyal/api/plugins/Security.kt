package com.sosyal.api.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.application.*

fun Application.configureSecurity() {
    install(Authentication) {
        jwt {

        }
    }
}
