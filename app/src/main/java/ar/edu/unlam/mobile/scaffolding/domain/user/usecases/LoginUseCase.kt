package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class LoginUseCase(
    private val userRepository: IUserRepository,
    private val authToken: AuthToken,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ) {
        val token = userRepository.login(email, password)
        authToken.userToken = token

        val user = userRepository.getUserProfile()
        authToken.cachedUser = user
    }
}
