package com.sosyal.api.data.dto

data class CommentDto(
    val id: String? = null,
    val postId: String,
    val content: String
)
