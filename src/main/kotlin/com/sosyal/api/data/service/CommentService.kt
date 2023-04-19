package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.Comment
import com.sosyal.api.data.entity.Post
import org.litote.kmongo.Id
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class CommentService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val commentsCollection = database.getCollection<Comment>("comments")

    fun addComment(comment: Comment): Id<Comment>? {
        val result = commentsCollection.insertOne(comment)

        return if (result.wasAcknowledged()) comment.id else null
    }

    fun getCommentsByPostId(postId: Id<Post>) =
        commentsCollection.find(Comment::postId eq postId).toList()
}