package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.dto.FavoriteDto
import com.sosyal.api.data.mapper.toFavorite
import com.sosyal.api.data.mapper.toFavoriteDto
import com.sosyal.api.data.repository.FavoriteRepository
import com.sosyal.api.data.service.FavoriteService
import org.bson.types.ObjectId

class FavoriteRepositoryImpl(
    private val favoriteService: FavoriteService
) : FavoriteRepository {
    override suspend fun addFavorite(favoriteDto: FavoriteDto) =
        favoriteService.addFavorite(favoriteDto.toFavorite()).toString()

    override suspend fun deleteFavorite(username: String, postId: String) =
        favoriteService.deleteFavorite(username = username, postId = ObjectId(postId))

    override suspend fun isPostFavorite(username: String, postId: String) =
        favoriteService.isPostFavorite(username = username, postId = ObjectId(postId))

    override suspend fun getFavoritesByPostId(postId: String): List<FavoriteDto> =
        favoriteService.getFavoritesByPostId(ObjectId(postId)).map { favorite ->
            favorite.toFavoriteDto()
        }
}