package com.sosyal.api.data.dto

data class CommentDto(
    val id: String? = null,
    val postId: String,
    val username: String,
    val userAvatar: String? = null,
    val content: String
)
