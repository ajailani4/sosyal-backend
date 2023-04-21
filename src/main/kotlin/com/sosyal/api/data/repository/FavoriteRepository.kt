package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.FavoriteDto

interface FavoriteRepository {
    fun addFavorite(favoriteDto: FavoriteDto): String
    fun deleteFavorite(username: String, postId: String): Boolean
    fun isPostFavorite(username: String, postId: String): Boolean
    fun getFavoritesByPostId(postId: String): List<FavoriteDto>
}