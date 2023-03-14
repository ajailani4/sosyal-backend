package com.sosyal.api.data.repository.impl

import com.sosyal.api.data.model.request.RegisterRequest
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.data.service.UserService

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override fun addUser(registerRequest: RegisterRequest) = userService.addUser(registerRequest)

    override fun getUser(username: String) = userService.getUser(username)
}