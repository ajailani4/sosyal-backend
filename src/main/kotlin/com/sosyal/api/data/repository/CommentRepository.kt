package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.CommentDto

interface CommentRepository {
    fun addComment(commentDto: CommentDto): String
}