package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.Post
import org.litote.kmongo.*
import org.litote.kmongo.util.idValue

class PostService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val postCollection = database.getCollection<Post>("posts")

    fun addPost(post: Post): Id<Post>? {
        val result = postCollection.insertOne(post)

        return if (result.wasAcknowledged()) post.id else null
    }

    fun getAllPosts() = postCollection.find().toList()

    fun getPost(id: Id<Post>) = postCollection.findOneById(id)

    fun editPost(id: Id<Post>, post: Post): Id<Post>? {
        val result = postCollection.updateOneById(id, post)

        return if (result.wasAcknowledged()) id else null
    }

    fun deletePost(id: Id<Post>): Boolean {
        val result = postCollection.deleteOneById(id)

        return result.wasAcknowledged()
    }
}