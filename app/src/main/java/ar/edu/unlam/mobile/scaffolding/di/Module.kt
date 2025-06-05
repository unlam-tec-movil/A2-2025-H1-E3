package ar.edu.unlam.mobile.scaffolding.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
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

@Module
@InstallIn(SingletonComponent::class)
object Module {
    @Provides
    @Singleton
    fun providerTokenManager(
        @ApplicationContext context: Context,
    ): TokenManager = TokenManager(context)

    @Provides
    @Singleton
    fun providerAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Provides
    fun providerApiService(tokenManager: TokenManager): ApiService =
        RetrofitService.createApiService(
            tokenManager,
        )

    @Provides
    fun providerPostRepository(
        apiService: ApiService
    ): IPostRepository = PostRepository(apiService)

    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        tokenManager: TokenManager,
    ): IUserRepository = UserRepository(apiService, tokenManager)
}