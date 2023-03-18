package com.sosyal.api.data.repository

import com.sosyal.api.data.entity.Post
import org.litote.kmongo.Id

interface PostRepository {
    fun addPost(post: Post): Id<Post>?
    fun getAllPosts(): List<Post>
    fun getPost(id: Id<Post>): Post?
    fun editPost(id: Id<Post>, post: Post): Id<Post>?
}