package com.sosyal.api.util

import io.ktor.websocket.*

data class Connection(
    val session: DefaultWebSocketSession,
    val username: String
)