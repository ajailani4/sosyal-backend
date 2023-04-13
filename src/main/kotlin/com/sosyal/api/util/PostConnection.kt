package com.sosyal.api.util

import io.ktor.websocket.*

data class PostConnection(
    val session: DefaultWebSocketSession,
    val username: String
)