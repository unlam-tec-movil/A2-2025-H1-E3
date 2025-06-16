package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(
    private val userRepository: IUserRepository,
){
    operator fun invoke(userId: Int): Flow<List<User>> = userRepository.getUser(userId)
}