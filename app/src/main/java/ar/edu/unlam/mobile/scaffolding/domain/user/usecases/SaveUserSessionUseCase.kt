package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.ISessionRepository

class SaveUserSessionUseCase(
    private val sessionManager: ISessionRepository,
) {
    suspend operator fun invoke(user: User) {
        sessionManager.saveUser(user)
    }
}
