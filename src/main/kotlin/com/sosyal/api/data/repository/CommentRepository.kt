package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.CommentDto
import javax.xml.stream.events.Comment

interface CommentRepository {
    suspend fun addComment(commentDto: CommentDto): String
    suspend fun getCommentsByPostId(postId: String): List<CommentDto>
}