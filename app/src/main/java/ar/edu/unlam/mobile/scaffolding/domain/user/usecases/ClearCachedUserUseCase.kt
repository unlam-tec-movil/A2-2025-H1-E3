package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class ClearCachedUserUseCase(
    private val repository: IUserRepository,
) {
    suspend operator fun invoke() = repository.clearCachedUser()
}
