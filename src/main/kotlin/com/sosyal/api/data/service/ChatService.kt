package com.sosyal.api.data.service

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sosyal.api.data.entity.Chat
import kotlinx.coroutines.flow.toList

class ChatService(database: MongoDatabase) {
    private val chatsCollection = database.getCollection<Chat>("chats")

    suspend fun createChat(chat: Chat): Boolean {
        var isChatExists = false
        val chats = chatsCollection.find().toList()

        chats.forEach {
            isChatExists = it.participants === chat.participants

            if (isChatExists) return@forEach
        }

        if (isChatExists) {
            return false
        }

        val result = chatsCollection.insertOne(chat)

        return result.wasAcknowledged()
    }
}