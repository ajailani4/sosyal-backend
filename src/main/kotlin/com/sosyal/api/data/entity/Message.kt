package com.sosyal.api.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Message(
    @BsonId
    val id: ObjectId? = null,
    val chatId: ObjectId,
    val username: String,
    val content: String
)
