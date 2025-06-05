package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authToken: AuthToken,
) : Interceptor {
    // https://square.github.io/okhttp/features/interceptors/
    // Retrofit interceptor documentación
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain
                .request()
                .newBuilder()
                .apply {
                    // Agregamos a las peticiones HTTP los headers que nesecita la API
                    addHeader("Authorization", authToken.userToken ?: "")
                    addHeader("Application-Token", authToken.appToken)
                }.build()
        return chain.proceed(request)
    }
}
