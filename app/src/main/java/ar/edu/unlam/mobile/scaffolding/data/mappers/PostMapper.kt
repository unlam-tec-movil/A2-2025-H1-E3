package ar.edu.unlam.mobile.scaffolding.data.mappers

import ar.edu.unlam.mobile.scaffolding.data.models.PostResponseDto
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

fun PostResponseDto.toDomain(): Post =
    Post(
        id = id,
        message = message,
        parentId = parentId,
        author = author,
        avatarUrl = avatarUrl,
        likes = likes,
        liked = liked,
        date = date,
    )

fun Post.toDto(): PostResponseDto =
    PostResponseDto(
        id = id,
        message = message,
        parentId = parentId,
        author = author,
        avatarUrl = avatarUrl,
        likes = likes,
        liked = liked,
        date = date,
    )
