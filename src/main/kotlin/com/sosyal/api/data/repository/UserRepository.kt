package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.UserDto
import com.sosyal.api.data.entity.User
import com.sosyal.api.data.dto.request.RegisterRequest

interface UserRepository {
    suspend fun addUser(registerRequest: RegisterRequest): Boolean
    suspend fun getUser(username: String): UserDto?
    suspend fun editUser(
        username: String,
        name: String,
        email: String,
        avatar: String?
    ): Boolean
}