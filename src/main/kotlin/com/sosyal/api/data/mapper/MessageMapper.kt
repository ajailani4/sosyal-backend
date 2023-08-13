package com.sosyal.api.data.mapper

import com.sosyal.api.data.dto.MessageDto
import com.sosyal.api.data.entity.Message

fun Message.toMessageDto() =
    MessageDto(
        id = id.toString(),
        content = content
    )

fun MessageDto.toMessage() =
    Message(content = content)