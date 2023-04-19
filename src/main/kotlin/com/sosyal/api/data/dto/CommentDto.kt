package com.sosyal.api.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: String? = null,
    val postId: String,
    val username: String,
    val userAvatar: String? = null,
    val content: String
)
