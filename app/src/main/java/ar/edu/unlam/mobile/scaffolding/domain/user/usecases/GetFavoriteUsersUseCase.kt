package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteUsersUseCase(
    private val userRepository: IUserRepository,
) {
    operator fun invoke(): Flow<List<User>> = userRepository.getAllFavoriteUser()
}
