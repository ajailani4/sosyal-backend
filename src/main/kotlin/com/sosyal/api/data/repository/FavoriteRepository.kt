package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.FavoriteDto

interface FavoriteRepository {
    suspend fun addFavorite(favoriteDto: FavoriteDto): String

    suspend fun deleteFavorite(username: String, postId: String): Boolean

    suspend fun isPostFavorite(username: String, postId: String): Boolean

    suspend fun getFavoritesByPostId(postId: String): List<FavoriteDto>
}