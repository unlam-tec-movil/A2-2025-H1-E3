package ar.edu.unlam.mobile.scaffolding.data.mappers

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities.FavoriteUser
import ar.edu.unlam.mobile.scaffolding.data.models.UserProfileResponseDto
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

fun UserProfileResponseDto.toDomain(): User =
    User(
        id = id,
        name = name,
        email = email,
        avatarUrl = avatarUrl,
    )

fun User.toFavoriteUser(): FavoriteUser =
    FavoriteUser(
        id = this.id,
        email = this.email,
        author = this.name,
        avatarUrl = this.avatarUrl,
    )

fun FavoriteUser.toUser(): User =
    User(
        id = id,
        email = this.email,
        name = this.author,
        avatarUrl = this.avatarUrl,
    )
