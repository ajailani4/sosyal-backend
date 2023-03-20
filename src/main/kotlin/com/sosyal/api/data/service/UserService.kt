package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.User
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class UserService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val userCollection = database.getCollection<User>("users")

    fun addUser(user: User): Boolean {
        val result = userCollection.insertOne(user)

        return result.wasAcknowledged()
    }

    fun getUser(username: String) = userCollection.findOne(User::username eq username)
}