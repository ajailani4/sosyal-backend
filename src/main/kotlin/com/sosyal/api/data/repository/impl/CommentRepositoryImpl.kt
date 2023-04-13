package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.dto.CommentDto
import com.sosyal.api.data.mapper.toComment
import com.sosyal.api.data.mapper.toCommentDto
import com.sosyal.api.data.repository.CommentRepository
import com.sosyal.api.data.service.CommentService
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import javax.xml.stream.events.Comment

class CommentRepositoryImpl(
    private val commentService: CommentService
) : CommentRepository {
    override fun addComment(commentDto: CommentDto) =
        commentService.addComment(commentDto.toComment()).toString()

    override fun getCommentsByPostId(postId: String) =
        commentService.getCommentsByPostId(ObjectId(postId).toId()).map { comment ->
            comment.toCommentDto()
        }
}