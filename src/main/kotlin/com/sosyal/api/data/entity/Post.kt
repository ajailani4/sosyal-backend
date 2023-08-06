package com.sosyal.api.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Post(
    @BsonId
    val id: ObjectId? = null,
    val username: String,
    val content: String,
    val likes: Int,
    val comments: Int,
    val date: String
)
