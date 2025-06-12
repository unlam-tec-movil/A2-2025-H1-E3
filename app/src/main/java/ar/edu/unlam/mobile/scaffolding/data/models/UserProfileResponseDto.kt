package ar.edu.unlam.mobile.scaffolding.data.models

import com.google.gson.annotations.SerializedName

data class UserProfileResponseDto(
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val email: String,
)
