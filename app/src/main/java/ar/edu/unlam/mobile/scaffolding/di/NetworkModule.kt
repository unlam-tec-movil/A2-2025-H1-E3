package ar.edu.unlam.mobile.scaffolding.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.AuthInterceptor
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419
// Inserción de dependencias con Hilt
// Y como inyectar los interceptores
// Nota: @Singleton para que solo haya una instancia durante el ciclo de vida de la app.
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // --- Proveedores de Infraestructura ---
    @Provides @Singleton
    fun providerAuthToken(
        @ApplicationContext context: Context,
    ): AuthToken = AuthToken(context)

    @Provides @Singleton
    fun providerAuthInterceptor(authToken: AuthToken): AuthInterceptor = AuthInterceptor(authToken)

    @Provides @Singleton
    fun providerApiService(authToken: AuthToken): ApiService =
        RetrofitService.createApiService(
            authToken,
        )
}
