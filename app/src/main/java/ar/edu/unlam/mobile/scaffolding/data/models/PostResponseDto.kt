package ar.edu.unlam.mobile.scaffolding.data.models

import com.google.gson.annotations.SerializedName

data class PostResponseDto(
    val id: Int,
    val message: String,
    @SerializedName("parent_id") val parentId: Int,
    val author: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val likes: Long,
    val liked: Boolean,
    val date: String,
)
