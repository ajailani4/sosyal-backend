package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.dto.FavoriteDto
import com.sosyal.api.data.mapper.toFavorite
import com.sosyal.api.data.mapper.toFavoriteDto
import com.sosyal.api.data.repository.FavoriteRepository
import com.sosyal.api.data.service.FavoriteService
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class FavoriteRepositoryImpl(
    private val favoriteService: FavoriteService
) : FavoriteRepository {
    override fun addFavorite(favoriteDto: FavoriteDto) =
        favoriteService.addFavorite(favoriteDto.toFavorite()).toString()

    override fun deleteFavorite(username: String, postId: String) =
        favoriteService.deleteFavorite(username = username, postId = ObjectId(postId).toId())

    override fun isPostFavorite(username: String, postId: String) =
        favoriteService.isPostFavorite(username = username, postId = ObjectId(postId).toId())

    override fun getFavoritesByPostId(postId: String): List<FavoriteDto> =
        favoriteService.getFavoritesByPostId(ObjectId(postId).toId()).map { favorite ->
            favorite.toFavoriteDto()
        }
}