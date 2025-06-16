package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.FavoriteUserDao
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.repositories.PostRepository
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    // --- Repositorios ---
    @Provides
    fun providerPostRepository(apiService: ApiService): IPostRepository = PostRepository(apiService)

    @Provides @Singleton
    fun providerUserRepository(
        apiService: ApiService,
        authToken: AuthToken,
        favoriteUserDao: FavoriteUserDao,
    ): IUserRepository = UserRepository(apiService, authToken, favoriteUserDao)
}
