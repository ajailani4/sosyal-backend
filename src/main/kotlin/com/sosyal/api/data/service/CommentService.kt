package com.sosyal.api.data.service

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sosyal.api.data.entity.Comment
import com.sosyal.api.data.entity.Post
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class CommentService(database: MongoDatabase) {
    private val commentsCollection = database.getCollection<Comment>("comments")

    suspend fun addComment(comment: Comment): ObjectId? {
        val result = commentsCollection.insertOne(comment)

        return if (result.wasAcknowledged()) comment.id else null
    }

    suspend fun getCommentsByPostId(postId: ObjectId) =
        commentsCollection.find(eq("postId", postId)).toList()
}