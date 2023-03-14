package com.sosyal.api

import com.sosyal.api.di.appModule
import com.sosyal.api.plugins.configureRouting
import com.sosyal.api.plugins.configureSecurity
import com.sosyal.api.plugins.configureSerialization
import com.sosyal.api.plugins.configureSockets
import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    configureSerialization()
    configureSockets()
    configureSecurity()
    configureRouting()
}
