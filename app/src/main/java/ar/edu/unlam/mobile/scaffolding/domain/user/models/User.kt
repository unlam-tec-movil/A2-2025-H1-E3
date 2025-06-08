package ar.edu.unlam.mobile.scaffolding.domain.user.models

import com.google.gson.annotations.SerializedName

data class User(
    val id: Int? = null,
    val email: String,
    val name: String,
    val password: String,
    @SerializedName("avatar_url")val avatarUrl: String,
)
