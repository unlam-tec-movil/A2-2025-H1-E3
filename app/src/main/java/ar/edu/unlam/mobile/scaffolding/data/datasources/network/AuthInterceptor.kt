package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import android.util.Log
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenManager: TokenManager,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().apply {
            addHeader("Authorization", tokenManager.userToken ?: "")
            addHeader("Application-Token", tokenManager.appToken)
        }.build()
        Log.d("AuthInterceptor", "Token: ${tokenManager.appToken}")
        return chain.proceed(request)
    }
}