package com.sosyal.api.di

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.sosyal.api.data.repository.CommentRepository
import com.sosyal.api.data.repository.FavoriteRepository
import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.data.repository.impl.CommentRepositoryImpl
import com.sosyal.api.data.repository.impl.FavoriteRepositoryImpl
import com.sosyal.api.data.repository.impl.PostRepositoryImpl
import com.sosyal.api.data.repository.impl.UserRepositoryImpl
import com.sosyal.api.data.service.CommentService
import com.sosyal.api.data.service.FavoriteService
import com.sosyal.api.data.service.PostService
import com.sosyal.api.data.service.UserService
import com.mongodb.kotlin.client.coroutine.MongoDatabase

import org.koin.dsl.module

val appModule = module {
    single<MongoDatabase> {
        val mongoClient = MongoClient.create(System.getenv("MONGODB_URL"))

        mongoClient.getDatabase(System.getenv("DB_NAME"))
    }

    single { UserService(get()) }
    single { PostService(get()) }
    single { FavoriteService(get()) }
    single { CommentService(get()) }

    single<UserRepository> { UserRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    single<CommentRepository> { CommentRepositoryImpl(get()) }
}