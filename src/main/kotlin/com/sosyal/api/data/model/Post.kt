package com.sosyal.api.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Post(
    @BsonId
    val id: Id<Post>? = null,
    val content: String,
    val date: String,
    val username: String
)
