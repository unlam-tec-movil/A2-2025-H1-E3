package ar.edu.unlam.mobile.scaffolding.domain.user.repository

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): String

    fun getUser(userId: Int): Flow<List<User>>
}
