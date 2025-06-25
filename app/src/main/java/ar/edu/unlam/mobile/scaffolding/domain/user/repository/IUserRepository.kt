package ar.edu.unlam.mobile.scaffolding.domain.user.repository

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): String

    suspend fun login(
        email: String,
        password: String,
    ): String

    suspend fun getUserProfile(): User

    suspend fun insertFavoriteUser(user: User)

    suspend fun deleteFavoriteUser(user: User)

    fun getAllFavoriteUser(): Flow<List<User>>

    suspend fun editUser(
        name: String,
        avatarUrl: String,
        password: String,
    ): User
}
