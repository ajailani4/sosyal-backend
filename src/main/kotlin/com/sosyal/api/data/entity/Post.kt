package com.sosyal.api.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Post(
    @BsonId
    val id: Id<Post>? = null,
    val username: String,
    val content: String,
    val likes: Int,
    val comments: Int,
    val date: String
)
