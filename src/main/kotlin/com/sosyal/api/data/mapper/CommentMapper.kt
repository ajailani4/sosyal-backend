package com.sosyal.api.data.mapper

import com.sosyal.api.data.dto.CommentDto
import com.sosyal.api.data.entity.Comment
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

fun CommentDto.toComment() =
    Comment(
        postId = ObjectId(postId).toId(),
        username = username,
        userAvatar = userAvatar,
        content = content
    )

fun Comment.toCommentDto() =
    CommentDto(
        id = id.toString(),
        postId = postId.toString(),
        username = username,
        userAvatar = userAvatar,
        content = content
    )