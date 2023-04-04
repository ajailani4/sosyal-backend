package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.dto.PostDto
import com.sosyal.api.data.mapper.toPost
import com.sosyal.api.data.mapper.toPostDto
import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.data.service.PostService
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId

class PostRepositoryImpl(private val postService: PostService) : PostRepository {
    override fun addPost(postDto: PostDto) = postService.addPost(postDto.toPost()).toString()

    override fun getAllPosts() = postService.getAllPosts().map { post -> post.toPostDto() }

    override fun getPost(id: String) = postService.getPost(ObjectId(id).toId())?.toPostDto()

    override fun editPost(id: String, postDto: PostDto) = postService.editPost(
        id = ObjectId(id).toId(),
        post = postDto.toPost()
    ).toString()

    override fun deletePost(id: String) = postService.deletePost(ObjectId(id).toId())
}