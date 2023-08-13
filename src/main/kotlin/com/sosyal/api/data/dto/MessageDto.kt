package com.sosyal.api.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String? = null,
    val content: String
)
