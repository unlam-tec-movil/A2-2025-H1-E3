package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class UpdateUserProfileUseCase(
    private val userRepository: IUserRepository,
) {
    suspend operator fun invoke(
        name: String,
        avatarUrl: String,
        password: String,
    ) {
        userRepository.editUser(name, avatarUrl, password)
    }
}
