package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.Favorite
import com.sosyal.api.data.entity.Post
import org.litote.kmongo.*

class FavoriteService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val favoritesCollection = database.getCollection<Favorite>("favorites")

    fun addFavorite(favorite: Favorite): Id<Favorite>? {
        val result = favoritesCollection.insertOne(favorite)

        return if (result.wasAcknowledged()) favorite.id else null
    }

    fun deleteFavorite(username: String, postId: Id<Post>): Boolean {
        val result = favoritesCollection.deleteOne(
            Favorite::username eq username,
            Favorite::postId eq postId
        )

        return result.wasAcknowledged()
    }

    fun isPostFavorite(username: String, postId: Id<Post>): Boolean {
        val result = favoritesCollection.findOne(
            Favorite::username eq username,
            Favorite::postId eq postId
        )

        return result != null
    }

    fun getFavoritesByPostId(postId: Id<Post>) =
        favoritesCollection.find(Favorite::postId eq postId).toList()
}