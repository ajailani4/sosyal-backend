package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.Comment
import org.litote.kmongo.Id
import org.litote.kmongo.getCollection

class CommentService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val commentsCollection = database.getCollection<Comment>("comments")

    fun addComment(comment: Comment): Id<Comment>? {
        val result = commentsCollection.insertOne(comment)

        return if (result.wasAcknowledged()) comment.id else null
    }
}