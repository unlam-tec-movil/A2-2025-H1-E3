package ar.edu.unlam.mobile.scaffolding.di

import android.content.Context
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.TokenManager
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.AuthInterceptor
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.RetrofitService
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
}