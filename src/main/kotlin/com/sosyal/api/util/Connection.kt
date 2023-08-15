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

data class ChatConnection(
    val session: DefaultWebSocketSession,
    val chatId: String,
    val username: String
)