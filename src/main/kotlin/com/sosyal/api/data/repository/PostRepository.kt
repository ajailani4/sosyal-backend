package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.PostDto

interface PostRepository {
    suspend fun addPost(postDto: PostDto): String
    suspend fun getAllPosts(): List<PostDto>
    suspend fun getPost(id: String): PostDto?
    suspend fun editPost(id: String, postDto: PostDto): String
    suspend fun deletePost(id: String): Boolean
}