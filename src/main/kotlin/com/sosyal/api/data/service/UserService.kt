package com.sosyal.api.data.service

import com.mongodb.client.MongoClient
import com.sosyal.api.data.entity.User
import org.litote.kmongo.*

class UserService(client: MongoClient) {
    private val database = client.getDatabase(System.getenv("DB_NAME"))
    private val usersCollection = database.getCollection<User>("users")

    fun addUser(user: User): Boolean {
        val result = usersCollection.insertOne(user)

        return result.wasAcknowledged()
    }

    fun getUser(username: String) = usersCollection.findOne(User::username eq username)

    fun editUser(
        username: String,
        name: String,
        email: String,
        avatar: String? = null
    ): Boolean {
        val updateQuery = if (avatar != null) {
            "{\$set: {name: '$name', email: '$email', avatar: '$avatar'}}"
        } else {
            "{\$set: {name: '$name', email: '$email'}}"
        }
        val result = usersCollection.updateOne(
            "{username: '$username'}",
            updateQuery
        )

        return result.wasAcknowledged()
    }
}