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

    fun addPost(post: Post): Id<Post>? {
        postCollection.insertOne(post)

        return post.id
    }

    fun getAllPosts() = postCollection.find().toList()

    fun getPost(id: Id<Post>) = postCollection.findOneById(id)

    fun editPost(id: Id<Post>, post: Post): Id<Post>? {
        postCollection.updateOneById(id, post)

        return post.id
    }
}