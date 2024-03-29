package com.sosyal.api.plugins

import com.sosyal.api.routes.configureChatRoutes
import com.sosyal.api.routes.configureCommentRoutes
import com.sosyal.api.routes.configurePostRoutes
import com.sosyal.api.util.ChatConnection
import com.sosyal.api.util.CommentConnection
import com.sosyal.api.util.PostConnection
import io.ktor.server.websocket.*
import java.time.Duration
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.util.Collections

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    routing {
        val postConnections = Collections.synchronizedSet<PostConnection?>(LinkedHashSet())
        val commentConnections = Collections.synchronizedSet<CommentConnection?>(LinkedHashSet())
        val chatConnections = Collections.synchronizedSet<ChatConnection>(LinkedHashSet())

        configurePostRoutes(postConnections)
        configureCommentRoutes(commentConnections)
        configureChatRoutes(chatConnections)
    }
}
