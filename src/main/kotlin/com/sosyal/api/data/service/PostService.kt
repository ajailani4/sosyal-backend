package com.sosyal.api.data.service

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sosyal.api.data.entity.Post
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class PostService(database: MongoDatabase) {
    private val postsCollection = database.getCollection<Post>("posts")

    suspend fun addPost(post: Post): ObjectId? {
        val result = postsCollection.insertOne(post)

        return if (result.wasAcknowledged()) post.id else null
    }

    suspend fun getAllPosts() = postsCollection.find().toList()

    suspend fun getPost(id: ObjectId): Post? = postsCollection.find(eq("_id", id)).firstOrNull()

    suspend fun editPost(id: ObjectId, post: Post): ObjectId? {
        val result = postsCollection.replaceOne(eq("_id", id), post)

        return if (result.wasAcknowledged()) id else null
    }

    suspend fun deletePost(id: ObjectId): Boolean {
        val result = postsCollection.deleteOne(eq("_id", id))

        return result.wasAcknowledged()
    }
}