package ar.edu.unlam.mobile.scaffolding.domain.user.usecases

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken

class LogoutUseCase(
    private val authToken: AuthToken,
) {
    operator fun invoke() {
        // Limpiamos el token guardado en SharedPreferences
        authToken.userToken = null
    }
}
