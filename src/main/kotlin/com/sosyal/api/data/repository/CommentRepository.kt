package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.CommentDto
import javax.xml.stream.events.Comment

interface CommentRepository {
    fun addComment(commentDto: CommentDto): String
    fun getCommentsByPostId(postId: String): List<CommentDto>
}