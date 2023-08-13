package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.dto.ChatDto
import com.sosyal.api.data.dto.MessageDto
import com.sosyal.api.data.entity.Chat
import com.sosyal.api.data.mapper.toChat
import com.sosyal.api.data.mapper.toChatDto
import com.sosyal.api.data.mapper.toMessageDto
import com.sosyal.api.data.repository.ChatRepository
import com.sosyal.api.data.service.ChatService
import org.bson.types.ObjectId

class ChatRepositoryImpl(
    private val chatService: ChatService
) : ChatRepository {
    override suspend fun getChatsByParticipants(username: String) =
        chatService.getChatsByUsername(username).map { it.toChatDto() }

    override suspend fun createChat(chatDto: ChatDto) =
        chatService.createChat(chatDto.toChat())

    override suspend fun getMessagesByChatId(chatId: String) =
        chatService.getMessagesByChatId(ObjectId(chatId)).map { it.toMessageDto() }
}