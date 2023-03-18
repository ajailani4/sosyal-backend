package com.sosyal.api.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    val id: String? = null,
    val username: String,
    val content: String,
    val like: Int,
    val date: String
)
