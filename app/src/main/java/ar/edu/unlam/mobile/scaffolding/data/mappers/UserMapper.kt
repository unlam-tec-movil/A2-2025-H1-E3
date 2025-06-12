package ar.edu.unlam.mobile.scaffolding.data.mappers

import ar.edu.unlam.mobile.scaffolding.data.models.UserProfileResponseDto
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

fun UserProfileResponseDto.toDomain(): User =
    User(
        name = name,
        email = email,
        avatarUrl = avatarUrl,
    )
