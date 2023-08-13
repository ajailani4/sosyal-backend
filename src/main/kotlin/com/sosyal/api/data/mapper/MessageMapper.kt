package com.sosyal.api.data.mapper

import com.sosyal.api.data.dto.MessageDto
import com.sosyal.api.data.entity.Message
import org.bson.types.ObjectId

fun MessageDto.toMessage() =
    Message(
        chatId = ObjectId(chatId),
        content = content
    )

fun Message.toMessageDto() =
    MessageDto(
        id = id.toString(),
        chatId = chatId.toString(),
        content = content
    )