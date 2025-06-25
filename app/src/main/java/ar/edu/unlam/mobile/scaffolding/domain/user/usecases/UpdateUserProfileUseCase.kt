package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.ISessionRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class UpdateUserProfileUseCase(
    private val userRepository: IUserRepository,
    private val sessionManager: ISessionRepository,
) {
    suspend operator fun invoke(
        name: String,
        avatarUrl: String,
        password: String,
    ) {
        val user = userRepository.editUser(name, avatarUrl, password)
        sessionManager.saveUser(user)
    }
}
