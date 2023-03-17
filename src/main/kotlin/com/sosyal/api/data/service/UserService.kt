package com.sosyal.api.data.service

import at.favre.lib.crypto.bcrypt.BCrypt
import com.mongodb.client.MongoClient
import com.sosyal.api.data.model.request.RegisterRequest
import com.sosyal.api.data.model.User
import org.litote.kmongo.*

class UserService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val userCollection = database.getCollection<User>("user")

    fun addUser(registerRequest: RegisterRequest): Boolean {
        val hashedPassword = BCrypt.withDefaults().hashToString(10, registerRequest.password.toCharArray())
        val result = userCollection.insertOne(
            User(
                name = registerRequest.name,
                email = registerRequest.email,
                username = registerRequest.username,
                password = hashedPassword
            )
        )

        return result.wasAcknowledged()
    }

    fun getUser(username: String) = userCollection.findOne(User::username eq username)
}