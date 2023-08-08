package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.ChatDto
import com.sosyal.api.data.entity.Chat

interface ChatRepository {
    suspend fun getChatsByParticipants(username: String): List<ChatDto>

    suspend fun createChat(chatDto: ChatDto): Boolean
}