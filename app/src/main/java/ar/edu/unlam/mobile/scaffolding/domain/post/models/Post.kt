package ar.edu.unlam.mobile.scaffolding.domain.post.models

import com.google.gson.annotations.SerializedName

data class Post(
    val id: Int,
    val message: String,
    @SerializedName("parent_id") val parentId: Int,
    val author: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    val likes: Int,
    val liked: Boolean,
    val date: String,
)
