package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.Post
import org.litote.kmongo.Id
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection
import org.litote.kmongo.updateOneById

class PostService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val postCollection = database.getCollection<Post>("posts")

    fun addPost(post: Post): Boolean {
        val result = postCollection.insertOne(post)

        return result.wasAcknowledged()
    }

    fun getAllPosts() = postCollection.find().toList()

    fun getPost(id: Id<Post>) = postCollection.findOneById(id)

    fun editPost(id: Id<Post>, post: Post): Boolean {
        val result = postCollection.updateOneById(id, post)

        return result.wasAcknowledged()
    }
}