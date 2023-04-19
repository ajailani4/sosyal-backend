package com.sosyal.api.util

import io.ktor.websocket.*

data class PostConnection(
    val session: DefaultWebSocketSession,
    val username: String
)

data class CommentConnection(
    val session: DefaultWebSocketSession,
    val username: String,
    val postId: String
)