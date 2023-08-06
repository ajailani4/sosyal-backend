package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.dto.PostDto
import com.sosyal.api.data.mapper.toPost
import com.sosyal.api.data.mapper.toPostDto
import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.data.service.PostService
import com.sosyal.api.data.service.UserService
import org.bson.types.ObjectId

class PostRepositoryImpl(
    private val postService: PostService,
    private val userService: UserService
) : PostRepository {
    override suspend fun addPost(postDto: PostDto) = postService.addPost(postDto.toPost()).toString()

    override suspend fun getAllPosts() = postService.getAllPosts().map { post ->
        val userDto = userService.getUser(post.username)
        post.toPostDto(userDto?.avatar)
    }

    override suspend fun getPost(id: String) = postService.getPost(ObjectId(id))?.toPostDto()

    override suspend fun editPost(id: String, postDto: PostDto) = postService.editPost(
        id = ObjectId(id),
        post = postDto.toPost()
    ).toString()

    override suspend fun deletePost(id: String) = postService.deletePost(ObjectId(id))
}