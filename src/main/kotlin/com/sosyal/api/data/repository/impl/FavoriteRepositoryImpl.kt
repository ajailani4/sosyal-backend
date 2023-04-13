package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.dto.FavoriteDto
import com.sosyal.api.data.mapper.toFavorite
import com.sosyal.api.data.repository.FavoriteRepository
import com.sosyal.api.data.service.FavoriteService

class FavoriteRepositoryImpl(
    private val favoriteService: FavoriteService
) : FavoriteRepository {
    override fun addFavorite(favoriteDto: FavoriteDto) =
        favoriteService.addFavorite(favoriteDto.toFavorite()).toString()

    override fun deleteFavorite(username: String, postId: String) =
        favoriteService.deleteFavorite(username = username, postId = postId)

    override fun isPostFavorite(username: String, postId: String) =
        favoriteService.isPostFavorite(username = username, postId = postId)

    override fun getFavoriteByPostId(postId: String) =
        favoriteService.getFavoriteByPostId(postId)
}