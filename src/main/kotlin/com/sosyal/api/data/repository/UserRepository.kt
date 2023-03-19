package com.sosyal.api.data.repository

import com.sosyal.api.data.dto.UserDto
import com.sosyal.api.data.entity.User
import com.sosyal.api.data.dto.request.RegisterRequest

interface UserRepository {
    fun addUser(registerRequest: RegisterRequest): Boolean
    fun getUser(username: String): UserDto?
}