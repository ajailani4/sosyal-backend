package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.Post
import org.litote.kmongo.*

class PostService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val postsCollection = database.getCollection<Post>("posts")

    fun addPost(post: Post): Id<Post>? {
        val result = postsCollection.insertOne(post)

        return if (result.wasAcknowledged()) post.id else null
    }

    fun getAllPosts() = postsCollection.find().toList()

    fun getPost(id: Id<Post>) = postsCollection.findOneById(id)

    fun editPost(id: Id<Post>, post: Post): Id<Post>? {
        val result = postsCollection.updateOneById(id, post)

        return if (result.wasAcknowledged()) id else null
    }

    fun deletePost(id: Id<Post>): Boolean {
        val result = postsCollection.deleteOneById(id)

        return result.wasAcknowledged()
    }
}