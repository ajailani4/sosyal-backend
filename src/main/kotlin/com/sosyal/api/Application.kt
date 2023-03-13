package com.sosyal.api

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.sosyal.api.plugins.configureRouting
import com.sosyal.api.plugins.configureSecurity
import com.sosyal.api.plugins.configureSerialization
import com.sosyal.api.plugins.configureSockets

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureSerialization()
    configureSockets()
    configureSecurity()
    configureRouting()
}
