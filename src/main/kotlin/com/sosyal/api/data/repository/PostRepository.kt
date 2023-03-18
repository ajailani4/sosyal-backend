package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.PostDto
import com.sosyal.api.data.entity.Post
import org.litote.kmongo.Id

interface PostRepository {
    fun addPost(postDto: PostDto): String
    fun getAllPosts(): List<PostDto>
    fun getPost(id: String): PostDto?
    fun editPost(id: String, postDto: PostDto): String
    fun deletePost(id: String): Boolean
}