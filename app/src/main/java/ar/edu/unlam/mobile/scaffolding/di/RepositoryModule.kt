package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.dao.DraftDao
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
    fun providerPostRepository(
        apiService: ApiService,
        draftDao: DraftDao,
    ): IPostRepository = PostRepository(apiService, draftDao)

    @Provides @Singleton
    fun providerUserRepository(
        apiService: ApiService,
        favoriteUserDao: FavoriteUserDao,
    ): IUserRepository = UserRepository(apiService, favoriteUserDao)
}
