package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class RemoveFavoriteUserUseCase(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(userId: Int) = repository.removeFavoriteUser(userId)
}
