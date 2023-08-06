package com.sosyal.api.data.mapper

import com.sosyal.api.data.dto.ChatDto
import com.sosyal.api.data.entity.Chat

fun ChatDto.toChat() =
    Chat(participants = participants)