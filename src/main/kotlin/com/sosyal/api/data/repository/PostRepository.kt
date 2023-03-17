package com.sosyal.api.data.repository

import com.sosyal.api.data.model.Post
import org.litote.kmongo.Id

interface PostRepository {
    fun addPost(post: Post): Id<Post>?
    fun getAllPosts(): List<Post>
    fun getPost(id: Id<Post>): Post?
}