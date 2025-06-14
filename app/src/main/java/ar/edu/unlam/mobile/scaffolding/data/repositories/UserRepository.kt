package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignInRequest
import ar.edu.unlam.mobile.scaffolding.data.mappers.toDomain
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import retrofit2.HttpException

class UserRepository(
    private val apiService: ApiService,
    private val authToken: AuthToken,
) : IUserRepository {
    override suspend fun register(
        name: String,
        email: String,
        password: String,
    ): String {
        try {
            val response = apiService.signIn(SignInRequest(name, email, password))
            if (response.isSuccessful) {
                return response.body()?.token ?: throw Exception("Token vacío")
            } else {
                throw Exception("Error ${response.code()}")
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun login(
        email: String,
        password: String,
    ): String {
        val response = apiService.login(LoginRequest(email, password))
        if (response.isSuccessful) {
            return response.body()?.token ?: throw Exception("Token vacío")
        } else {
            throw Exception("Error ${response.code()}")
        }
    }

    override suspend fun isUserLogged(): Boolean = !authToken.userToken.isNullOrEmpty()

    override suspend fun getUserProfile(): User =
        try {
            val responseDto = apiService.getUserProfile()
            responseDto.toDomain()
        } catch (e: Exception) {
            throw e
        }

    override suspend fun getCachedUser(): User? = authToken.cachedUser

    override suspend fun saveCachedUser(user: User) {
        authToken.cachedUser = user
    }

    override suspend fun clearCachedUser() {
        authToken.cachedUser = null
    }
}
