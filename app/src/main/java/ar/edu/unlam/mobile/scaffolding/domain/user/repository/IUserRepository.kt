package ar.edu.unlam.mobile.scaffolding.domain.user.repository

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
}
