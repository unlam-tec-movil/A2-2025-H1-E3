package ar.edu.unlam.mobile.scaffolding.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.AuthInterceptor
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.RetrofitService
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// https://developer.android.com/training/dependency-injection/hilt-android?hl=es-419
// Inserción de dependencias con Hilt
// Y como inyectar los interceptores
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    // Singleton para que haya una unica instancia
    @Singleton
    fun providerAuthToken(
        @ApplicationContext context: Context,
    ): AuthToken = AuthToken(context)

    @Provides
    @Singleton
    fun providerAuthInterceptor(authToken: AuthToken): AuthInterceptor = AuthInterceptor(authToken)

    @Provides
    fun providerApiService(authToken: AuthToken): ApiService =
        RetrofitService.createApiService(
            authToken,
        )

    @Provides
    fun providerPostRepository(apiService: ApiService): IPostRepository = PostRepository(apiService)

    @Provides
    fun providerUserRepository(
        apiService: ApiService,
        authToken: AuthToken,
    ): IUserRepository = UserRepository(apiService, authToken)
}
