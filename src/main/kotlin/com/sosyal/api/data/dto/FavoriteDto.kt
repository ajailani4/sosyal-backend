package com.sosyal.api.data.dto

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class FavoriteDto(
    val id: String?,
    val username: String,
    val postId: String
)