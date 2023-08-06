package com.sosyal.api.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Favorite(
    @BsonId
    val id: ObjectId? = null,
    val username: String,
    val postId: ObjectId
)