package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.ChatDto
import com.sosyal.api.data.dto.MessageDto
import com.sosyal.api.data.entity.Chat

interface ChatRepository {
    suspend fun getChatsByParticipants(username: String): List<ChatDto>

    suspend fun createChat(chatDto: ChatDto): Boolean

    suspend fun getMessagesByChatId(chatId: String): List<MessageDto>

    suspend fun addMessage(messageDto: MessageDto): String
}