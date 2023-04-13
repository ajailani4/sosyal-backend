package com.sosyal.api.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Comment(
    @BsonId
    val id: Id<Comment>? = null,
    val postId: Id<Post>,
    val content: String
)
