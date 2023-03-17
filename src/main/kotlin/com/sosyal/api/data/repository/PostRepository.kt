package com.sosyal.api.data.repository

import com.sosyal.api.data.model.Post

interface PostRepository {
    fun addPost(post: Post): Boolean
    fun getAllPosts(): List<Post>
}