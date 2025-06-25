package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.ISessionRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val sessionManager: ISessionRepository,
) : Interceptor {
    // https://square.github.io/okhttp/features/interceptors/
    // Retrofit interceptor documentación
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain
                .request()
                .newBuilder()
                .apply {
                    // Agregamos a las peticiones HTTP los headers que necesita la API
                    addHeader("Authorization", sessionManager.userToken ?: "")
                    addHeader("Application-Token", sessionManager.appToken.toString())
                }.build()
        return chain.proceed(request)
    }
}
