package ar.edu.unlam.mobile.scaffolding.domain.user.repository

interface IUserRepository {
    suspend fun isUserLogged(): Boolean

    suspend fun login(
        email: String,
        password: String,
    )
}
