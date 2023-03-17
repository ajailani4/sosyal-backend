package com.sosyal.api.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

@Serializable
data class Post(
    @BsonId
    val id: Id<Post>? = null,
    val username: String,
    val content: String,
    val like: String,
    val date: String,
)
