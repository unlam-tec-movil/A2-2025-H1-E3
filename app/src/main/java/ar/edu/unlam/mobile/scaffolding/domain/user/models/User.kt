package ar.edu.unlam.mobile.scaffolding.domain.user.models

data class User(
    val id: Int? = null,
    val email: String,
    val name: String,
    val password: String,
    val avatarUrl: String,
)
