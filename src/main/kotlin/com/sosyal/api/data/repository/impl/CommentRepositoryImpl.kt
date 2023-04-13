package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.dto.CommentDto
import com.sosyal.api.data.mapper.toComment
import com.sosyal.api.data.repository.CommentRepository
import com.sosyal.api.data.service.CommentService

class CommentRepositoryImpl(
    private val commentService: CommentService
) : CommentRepository {
    override fun addComment(commentDto: CommentDto) =
        commentService.addComment(commentDto.toComment()).toString()
}