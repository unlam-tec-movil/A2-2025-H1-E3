package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class GetUserProfileUseCase(
    private val userRepository: IUserRepository,
) {
    suspend operator fun invoke(): User = userRepository.getUserProfile()
}
