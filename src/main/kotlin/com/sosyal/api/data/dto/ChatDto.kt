package com.sosyal.api.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatDto(
    val id: String,
    val participants: List<UserDto>
)
