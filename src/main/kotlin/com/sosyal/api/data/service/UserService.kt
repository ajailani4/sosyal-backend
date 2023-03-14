package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.model.request.RegisterRequest
import com.sosyal.api.data.model.User
import org.litote.kmongo.*

class UserService(client: MongoClient) {
    private val database = client.getDatabase("sosyal_db")
    private val userCollection = database.getCollection<User>("user")

    fun addUser(registerRequest: RegisterRequest): Boolean {
        val result = userCollection.insertOne(
            User(
                name = registerRequest.name,
                email = registerRequest.email,
                username = registerRequest.username,
                password = registerRequest.password
            )
        )

        return result.wasAcknowledged()
    }

    fun getUser(username: String) = userCollection.findOne(User::username eq username)
}