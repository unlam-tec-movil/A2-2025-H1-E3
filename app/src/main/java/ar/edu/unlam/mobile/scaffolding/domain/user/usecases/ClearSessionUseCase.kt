package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.ISessionRepository

class ClearSessionUseCase(
    private val sessionManager: ISessionRepository,
) {
    suspend operator fun invoke() {
        sessionManager.clearSession()
    }
}
