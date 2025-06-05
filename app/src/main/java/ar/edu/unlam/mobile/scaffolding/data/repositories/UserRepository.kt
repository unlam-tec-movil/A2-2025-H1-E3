package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class UserRepository (
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : IUserRepository {

    override suspend fun isUserLogged(): Boolean {
        return !tokenManager.userToken.isNullOrEmpty()
    }

    override suspend fun login(email: String, password: String) {
        try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                response.body()?.let {
                    tokenManager.userToken = it.token
                }
            } else {
                throw Exception("Error ${response.code()}")
            }
        } catch (e: Exception) {
            throw e
        }
    }
}