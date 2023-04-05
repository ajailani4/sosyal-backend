package com.sosyal.api.di

import com.sosyal.api.data.repository.PostRepository
import com.sosyal.api.data.repository.UserRepository
import com.sosyal.api.data.repository.impl.PostRepositoryImpl
import com.sosyal.api.data.repository.impl.UserRepositoryImpl
import com.sosyal.api.data.service.PostService
import com.sosyal.api.data.service.UserService
import org.koin.dsl.module
import org.litote.kmongo.KMongo

val appModule = module {
    single { KMongo.createClient(System.getenv("MONGODB_URL")) }
    single { UserService(get()) }
    single { PostService(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<PostRepository> { PostRepositoryImpl(get(), get()) }
}