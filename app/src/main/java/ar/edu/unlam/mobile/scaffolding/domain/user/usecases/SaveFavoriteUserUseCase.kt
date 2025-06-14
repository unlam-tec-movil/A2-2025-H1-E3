package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class SaveFavoriteUserUseCase(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(user: User) = repository.saveFavoriteUser(user)
}
