package com.sosyal.api.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class Comment(
    @BsonId
    val id: ObjectId? = null,
    val postId: ObjectId,
    val username: String,
    @BsonProperty("string")
    val userAvatar: String? = null,
    val content: String
)
