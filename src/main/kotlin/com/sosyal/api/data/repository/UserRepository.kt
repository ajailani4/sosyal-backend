package com.sosyal.api.data.repository

import com.sosyal.api.data.model.User
import com.sosyal.api.data.model.request.RegisterRequest

interface UserRepository {
    fun addUser(registerRequest: RegisterRequest): Boolean
    fun getUser(username: String): User?
}