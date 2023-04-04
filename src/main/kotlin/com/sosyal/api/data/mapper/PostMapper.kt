package com.sosyal.api.data.mapper

import com.sosyal.api.data.dto.PostDto
import com.sosyal.api.data.entity.Post
import org.litote.kmongo.util.idValue

fun Post.toPostDto() =
    PostDto(
        id = id.toString(),
        username = username,
        content = content,
        likes = likes,
        comments = comments,
        date = date
    )

fun PostDto.toPost() =
    Post(
        username = username,
        content = content,
        likes = likes,
        comments = comments,
        date = date
    )