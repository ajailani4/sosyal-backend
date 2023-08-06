package com.sosyal.api.data.service

import com.mongodb.client.model.Filters.and
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sosyal.api.data.entity.Favorite
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class FavoriteService(database: MongoDatabase) {
    private val favoritesCollection = database.getCollection<Favorite>("favorites")

    suspend fun addFavorite(favorite: Favorite): ObjectId? {
        val result = favoritesCollection.insertOne(favorite)

        return if (result.wasAcknowledged()) favorite.id else null
    }

    suspend fun deleteFavorite(username: String, postId: ObjectId): Boolean {
        val result = favoritesCollection.deleteOne(
            and(eq("username", username), eq("postId", postId))
        )

        return result.wasAcknowledged()
    }

    suspend fun isPostFavorite(username: String, postId: ObjectId): Boolean {
        val result = favoritesCollection.find(
            and(eq("username", username), eq("postId", postId))
        ).firstOrNull()

        return result != null
    }

    suspend fun getFavoritesByPostId(postId: ObjectId) =
        favoritesCollection.find(eq("postId", postId)).toList()
}