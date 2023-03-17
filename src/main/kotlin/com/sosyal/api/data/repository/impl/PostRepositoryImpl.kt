package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.model.Post
import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.data.service.PostService
import org.litote.kmongo.Id

class PostRepositoryImpl(private val postService: PostService) : PostRepository {
    override fun addPost(post: Post) = postService.addPost(post)

    override fun getAllPosts() = postService.getAllPosts()

    override fun getPost(id: Id<Post>) = postService.getPost(id)
}