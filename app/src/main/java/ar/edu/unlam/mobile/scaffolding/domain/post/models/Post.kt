package ar.edu.unlam.mobile.scaffolding.domain.post.models

data class Post(
    val id: Int,
    val message: String,
    val parentId: Int,
    val author: String,
    val avatarUrl: String,
    val likes: Long,
    val liked: Boolean,
    val date: String,
)
