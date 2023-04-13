package com.sosyal.api.data.mapper

import com.sosyal.api.data.dto.FavoriteDto
import com.sosyal.api.data.entity.Favorite
import org.bson.types.ObjectId
import org.litote.kmongo.id.ObjectIdGenerator
import org.litote.kmongo.id.toId

fun FavoriteDto.toFavorite() =
    Favorite(
        username = username,
        postId = ObjectId(postId).toId()
    )
