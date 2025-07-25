package ar.edu.unlam.mobile.scaffolding.data.repositories

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.FavoriteUserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignInRequest
import ar.edu.unlam.mobile.scaffolding.data.mappers.toDomain
import ar.edu.unlam.mobile.scaffolding.data.mappers.toFavoriteUser
import ar.edu.unlam.mobile.scaffolding.data.mappers.toUser
import ar.edu.unlam.mobile.scaffolding.data.models.EditUserRequestDto
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class UserRepository(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao,
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
        try {
            val response = apiService.login(LoginRequest(email, password))
            if (response.isSuccessful) {
                return response.body()?.token ?: throw Exception("Token vacío")
            } else {
                Log.e(
                    "AuthRepository",
                    "Login failed - HTTP ${response.code()}: ${response.message()}",
                )

                when (response.code()) {
                    401 -> throw Exception("Credenciales incorrectas")
                    500, 502, 503 -> throw Exception("Servidor temporalmente no disponible")
                    else -> throw Exception("Error del servidor ${response.code()}")
                }
            }
        } catch (e: IOException) {
            throw Exception("Error de conexión. Verificá tu internet", e)
        }
    }

    override suspend fun getUserProfile(): User =
        try {
            val responseDto = apiService.getUserProfile()
            responseDto.toDomain()
        } catch (e: Exception) {
            throw e
        }

    override suspend fun insertFavoriteUser(user: User) {
        favoriteUserDao.insertFavoriteUser(user.toFavoriteUser())
    }

    override suspend fun deleteFavoriteUser(user: User) {
        favoriteUserDao.deleteFavoriteUser(user.toFavoriteUser())
    }

    override fun getAllFavoriteUser(): Flow<List<User>> =
        favoriteUserDao
            .getAllFavoriteUsers()
            .map { favoriteList -> favoriteList.map { it.toUser() } }

    override suspend fun editUser(
        name: String,
        avatarUrl: String,
        password: String,
    ): User {
        val request = EditUserRequestDto(name, avatarUrl, password)
        try {
            val response = apiService.editUser(request)
            if (response.isSuccessful) {
                val body = response.body()
                val updatedUser =
                    body?.toDomain()
                        ?: throw Exception("Respuesta del servidor vacía")

                return updatedUser
            } else {
                val error = response.errorBody()?.string()
                throw Exception("Error ${response.code()}: $error")
            }
        } catch (e: IOException) {
            throw Exception("Error de red: ${e.localizedMessage}", e)
        } catch (e: HttpException) {
            throw Exception("Error HTTP: ${e.localizedMessage}", e)
        } catch (e: Exception) {
            throw Exception("Error inesperado: ${e.localizedMessage}", e)
        }
    }
}
