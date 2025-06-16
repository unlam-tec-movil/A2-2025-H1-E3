package ar.edu.unlam.mobile.scaffolding.domain.user.models

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val avatarUrl: String,
)
