package com.sosyal.api.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val avatar: String? = null,
    val username: String,
    val password: String
)
