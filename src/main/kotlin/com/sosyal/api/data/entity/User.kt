package com.sosyal.api.data.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId

data class User(
    @BsonId
    val id: ObjectId? = null,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val username: String,
    val password: String
)
