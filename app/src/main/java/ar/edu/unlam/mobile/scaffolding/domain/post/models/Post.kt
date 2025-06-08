package ar.edu.unlam.mobile.scaffolding.domain.post.models

data class Post(
    val id: Int,
    val message: String,
    val parentId: Int,
    val author: String,
    val avatarUrl: String,
    val likes: Int,
    val liked: Boolean,
    val date: String,
)
