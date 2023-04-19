package com.sosyal.api.data.mapper

import com.sosyal.api.data.dto.UserDto
import com.sosyal.api.data.entity.User

fun User.toUserDto() =
    UserDto(
        id = id.toString(),
        name = name,
        email = email,
        avatar = avatar,
        username = username,
        password = password
    )