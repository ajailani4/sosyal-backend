package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.Favorite
import org.bson.types.ObjectId
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

class FavoriteService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val favoritesCollection = database.getCollection<Favorite>("favorites")

    fun addFavorite(favorite: Favorite): Id<Favorite>? {
        val result = favoritesCollection.insertOne(favorite)

        return if (result.wasAcknowledged()) favorite.id else null
    }

    fun deleteFavorite(username: String, postId: String): Boolean {
        val result = favoritesCollection.deleteOne(
            Favorite::username eq username,
            Favorite::postId eq ObjectId(postId).toId()
        )

        return result.wasAcknowledged()
    }

    fun isPostFavorite(username: String, postId: String): Boolean {
        val result = favoritesCollection.findOne(
            Favorite::username eq username,
            Favorite::postId eq ObjectId(postId).toId()
        )

        return result != null
    }

    fun getFavoriteByPostId(postId: String): Int {
        val result = favoritesCollection.find(Favorite::postId eq ObjectId(postId).toId())

        return result.toList().size
    }
}