package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.ISessionRepository
import kotlinx.coroutines.flow.Flow

class GetUserSessionUseCase(
    private val sessionManager: ISessionRepository,
) {
    operator fun invoke(): Flow<User?> = sessionManager.currentUser
}
