package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.domain.user.repository.ISessionRepository
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    // https://square.github.io/retrofit/
    // Documentacion de Retrofit
    fun createApiService(sessionManager: ISessionRepository): ApiService {
        val interceptor = AuthInterceptor(sessionManager)

        // Agregamos el interceptor
        val client =
            OkHttpClient
                .Builder()
                .addInterceptor(interceptor)
                .build()

        // Builder Como lo vimos en clase
        return Retrofit
            .Builder()
            .baseUrl("https://tuiter.fragua.com.ar/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
