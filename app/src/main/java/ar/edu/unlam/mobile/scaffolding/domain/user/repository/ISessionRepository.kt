package ar.edu.unlam.mobile.scaffolding.domain.user.repository

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import kotlinx.coroutines.flow.Flow

interface ISessionRepository {
    val currentUser: Flow<User?>

    var userToken: String?

    val appToken: String?

    suspend fun saveUser(user: User)

    suspend fun clearSession()
}
