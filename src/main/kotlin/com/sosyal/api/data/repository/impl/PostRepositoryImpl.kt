package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.model.Post
import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.data.service.PostService
import com.sosyal.api.data.service.UserService

class PostRepositoryImpl(private val postService: PostService) : PostRepository {
    override fun addPost(post: Post) = postService.addPost(post)

    override fun getAllPosts() = postService.getAllPosts()
}