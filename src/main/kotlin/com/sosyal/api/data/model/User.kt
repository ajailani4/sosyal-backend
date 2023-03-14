package com.sosyal.api.data.model

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id

data class User(
    @BsonId
    val id: Id<User>? = null,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val username: String,
    val password: String
)
