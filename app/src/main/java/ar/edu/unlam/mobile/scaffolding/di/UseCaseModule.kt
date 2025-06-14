package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.data.datasources.local.AuthToken
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreatePostUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreateReplyUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetPostsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetQuotesUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.ClearCachedUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetCachedUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserProfileUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.IsUserLoggedInUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.LoginUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.LogoutUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.SaveCachedUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.SignInUseCase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    // --- Use Cases ---
    @Provides
    fun provideGetPostsUseCase(repo: IPostRepository): GetPostsUseCase = GetPostsUseCase(repo)

    @Provides
    fun provideGetQuotesUseCase(repo: IPostRepository): GetQuotesUseCase = GetQuotesUseCase(repo)

    @Provides
    fun provideCreatePostUseCase(repo: IPostRepository): CreatePostUseCase = CreatePostUseCase(repo)

    @Provides
    fun provideCreateReplyUseCase(repo: IPostRepository): CreateReplyUseCase = CreateReplyUseCase(repo)

    @Provides
    fun provideLoginUseCase(
        repo: IUserRepository,
        authToken: AuthToken,
    ): LoginUseCase = LoginUseCase(repo, authToken)

    @Provides
    fun provideIsUserLoggedInUseCase(repo: IUserRepository): IsUserLoggedInUseCase = IsUserLoggedInUseCase(repo)

    @Provides
    fun provideGetUserProfileUseCase(repo: IUserRepository): GetUserProfileUseCase = GetUserProfileUseCase(repo)

    @Provides
    fun provideLogoutUseCase(authToken: AuthToken): LogoutUseCase = LogoutUseCase(authToken)

    @Provides

    fun provideClearCachedUserUseCase(repo: IUserRepository): ClearCachedUserUseCase = ClearCachedUserUseCase(repo)

    @Provides
    fun provideGetCachedUserUseCase(repo: IUserRepository): GetCachedUserUseCase = GetCachedUserUseCase(repo)

    @Provides
    fun provideSaveCachedUserUseCase(repo: IUserRepository): SaveCachedUserUseCase = SaveCachedUserUseCase(repo)

    fun provideSignInUseCase(
        repo: IUserRepository,
        authToken: AuthToken,
    ): SignInUseCase = SignInUseCase(repo, authToken)
}
