package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignInRequest
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import retrofit2.HttpException

class UserRepository(
    private val apiService: ApiService,
) : IUserRepository {
    override suspend fun register(
        name: String,
        email: String,
        password: String,
    ): String {
        val response = apiService.signIn(SignInRequest(name, email, password))
        if (response.isSuccessful) {
            return response.body()?.token ?: throw NoSuchElementException("Token vacio")
        } else {
            throw HttpException(response)
        }
    }
}
