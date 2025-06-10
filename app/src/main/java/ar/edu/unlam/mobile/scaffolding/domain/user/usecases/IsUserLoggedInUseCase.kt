package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository

class IsUserLoggedInUseCase(
    private val userRepository: IUserRepository,
) {
    suspend operator fun invoke(): Boolean = userRepository.isUserLogged()
}
