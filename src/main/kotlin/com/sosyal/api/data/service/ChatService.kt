package com.sosyal.api.data.service

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sosyal.api.data.entity.Chat
import com.sosyal.api.data.entity.Message
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class ChatService(database: MongoDatabase) {
    private val chatsCollection = database.getCollection<Chat>("chats")
    private val messagesCollection = database.getCollection<Message>("messages")

    suspend fun getChatsByUsername(username: String): List<Chat> {
        val chats = chatsCollection.find().toList()

        return chats.filter { it.participants.contains(username) }
    }

    suspend fun createChat(chat: Chat): Boolean {
        var isChatExists = false
        val chats = chatsCollection.find().toList()

        chats.forEach {
            isChatExists = it.participants == chat.participants

            if (isChatExists) return@forEach
        }

        if (isChatExists) {
            return false
        }

        val result = chatsCollection.insertOne(chat)

        return result.wasAcknowledged()
    }

    suspend fun getMessagesByChatId(chatId: ObjectId) =
        messagesCollection.find(eq("chatId", chatId)).toList()
}