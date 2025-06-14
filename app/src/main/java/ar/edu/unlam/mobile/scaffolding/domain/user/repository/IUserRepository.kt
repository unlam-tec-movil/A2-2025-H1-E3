package ar.edu.unlam.mobile.scaffolding.domain.user.repository

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

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

    suspend fun isUserLogged(): Boolean

    suspend fun getUserProfile(): User

    suspend fun getCachedUser(): User?

    suspend fun saveCachedUser(user: User)

    suspend fun clearCachedUser()

    suspend fun saveFavoriteUser(user: User)

    suspend fun getFavoriteUsers(): List<User>

    suspend fun removeFavoriteUser(userId: Int)
}
