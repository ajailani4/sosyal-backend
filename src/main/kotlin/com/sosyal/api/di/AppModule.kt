package com.sosyal.api.di

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sosyal.api.data.repository.*
import com.sosyal.api.data.repository.impl.*
import com.sosyal.api.data.service.*

import org.koin.dsl.module

val appModule = module {
    single {
        val mongoClient = MongoClient.create(System.getenv("MONGODB_URL"))

        mongoClient.getDatabase(System.getenv("DB_NAME"))
    }

    single { UserService(get()) }
    single { PostService(get()) }
    single { FavoriteService(get()) }
    single { CommentService(get()) }
    single { ChatService(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }
    single<ChatRepository> { ChatRepositoryImpl(get()) }
}