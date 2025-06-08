package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignInRequest
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import javax.inject.Inject

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)

class UserRepository
    @Inject
    constructor(
        private val apiService: ApiService,
        private val authToken: AuthToken,
    ) : IUserRepository {
        override suspend fun register(
            name: String,
            email: String,
            password: String,
        ) {
            try {
                val response = apiService.signIn(SignInRequest(name, email, password))
                if (response.isSuccessful) {
                    response.body()?.let {
                        authToken.userToken = it.token
                    }
                } else {
                    throw Exception("Error en el registro HTTP: ${response.code()}")
                }
            } catch (e: Exception) {
                throw Exception("Error en el registro: ${e.message}")
            }
        }
    }
