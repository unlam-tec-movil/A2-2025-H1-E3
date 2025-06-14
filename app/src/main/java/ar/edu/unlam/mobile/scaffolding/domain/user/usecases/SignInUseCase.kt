package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class SignInUseCase(
    private val userRepository: IUserRepository,
    private val authToken: AuthToken,
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
    ) {
        val token = userRepository.register(name, email, password)
        authToken.userToken = token
    }
}
