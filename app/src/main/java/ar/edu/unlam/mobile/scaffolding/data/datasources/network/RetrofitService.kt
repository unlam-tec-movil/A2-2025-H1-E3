package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    fun createApiService(tokenManager: TokenManager): ApiService {
        val interceptor = AuthInterceptor(tokenManager)
        val client =
            OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build()

        return Retrofit
            .Builder()
            .baseUrl("https://tuiter.fragua.com.ar/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
