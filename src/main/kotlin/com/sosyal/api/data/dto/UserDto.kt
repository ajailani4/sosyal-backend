package com.sosyal.api.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String? = null,
    val name: String,
    val email: String,
    val avatar: String? = null,
    val username: String
)
