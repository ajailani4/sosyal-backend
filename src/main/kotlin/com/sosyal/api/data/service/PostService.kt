package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.model.Post
import org.litote.kmongo.getCollection

class PostService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val postCollection = database.getCollection<Post>("posts")

    fun addPost(post: Post): Boolean {
        val result = postCollection.insertOne(post)

        return result.wasAcknowledged()
    }

    fun getAllPosts() = postCollection.find().toList()
}