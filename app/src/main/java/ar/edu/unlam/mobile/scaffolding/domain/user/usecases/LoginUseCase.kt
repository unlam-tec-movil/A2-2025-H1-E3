package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.ISessionRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class LoginUseCase(
    private val userRepository: IUserRepository,
    private val sessionManager: ISessionRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ) {
        val token = userRepository.login(email, password)
        sessionManager.userToken = token

        val user = userRepository.getUserProfile()
        sessionManager.saveUser(user)
    }
}
