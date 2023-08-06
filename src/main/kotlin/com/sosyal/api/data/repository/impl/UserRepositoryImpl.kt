package com.sosyal.api.data.repository.impl

import at.favre.lib.crypto.bcrypt.BCrypt
import com.sosyal.api.data.dto.UserDto
import com.sosyal.api.data.dto.request.RegisterRequest
import com.sosyal.api.data.entity.User
import com.sosyal.api.data.mapper.toUserDto
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.data.service.UserService

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override suspend fun addUser(registerRequest: RegisterRequest): Boolean {
        val hashedPassword = BCrypt.withDefaults().hashToString(10, registerRequest.password.toCharArray())

        return userService.addUser(
            User(
                name = registerRequest.name,
                email = registerRequest.email,
                username = registerRequest.username,
                password = hashedPassword
            )
        )
    }

    override suspend fun getUser(username: String) = userService.getUser(username)?.toUserDto()

    override suspend fun getUsers() = userService.getUsers().map { it.toUserDto() }

    override suspend fun editUser(
        username: String,
        name: String,
        email: String,
        avatar: String?
    ) = userService.editUser(
        username = username,
        name = name,
        email = email,
        avatar = avatar
    )
}