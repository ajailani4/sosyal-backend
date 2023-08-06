package com.sosyal.api.data.mapper

import com.sosyal.api.data.dto.FavoriteDto
import com.sosyal.api.data.entity.Favorite
import org.bson.types.ObjectId

fun FavoriteDto.toFavorite() =
    Favorite(
        username = username,
        postId = ObjectId(postId)
    )

fun Favorite.toFavoriteDto() =
    FavoriteDto(
        id = id.toString(),
        username = username,
        postId = postId.toString()
    )