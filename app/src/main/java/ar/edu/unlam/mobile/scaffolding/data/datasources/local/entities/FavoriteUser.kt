package ar.edu.unlam.mobile.scaffolding.data.datasources.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val email: String,
    val author: String,
    val avatarUrl: String,
)
