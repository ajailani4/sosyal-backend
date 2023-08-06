package com.sosyal.api.data.service

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.set
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sosyal.api.data.entity.User
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList

class UserService(database: MongoDatabase) {
    private val usersCollection = database.getCollection<User>("users")

    suspend fun addUser(user: User): Boolean {
        val result = usersCollection.insertOne(user)

        return result.wasAcknowledged()
    }

    suspend fun getUser(username: String): User? =
        usersCollection.find(eq("username", username)).firstOrNull()

    suspend fun getUsers() = usersCollection.find().toList()

    suspend fun editUser(
        username: String,
        name: String,
        email: String,
        avatar: String? = null
    ): Boolean {
        val updateFilter = if (avatar != null) {
            set("name", name)
            set("email", email)
            set("avatar", avatar)
        } else {
            set("name", name)
            set("email", email)
        }

        val result = usersCollection.updateOne(
            eq("username", username),
            updateFilter
        )

        return result.wasAcknowledged()
    }
}