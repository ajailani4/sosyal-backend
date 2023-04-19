package com.sosyal.api.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class Favorite(
    @BsonId
    val id: Id<Favorite>? = null,
    val username: String,
    val postId: Id<Post>? = null
)