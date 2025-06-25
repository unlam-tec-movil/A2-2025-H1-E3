package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreatePostUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreateReplyUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.DeleteDraftUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetDraftUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetPostsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetQuotesUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.SaveDraftUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.ToggleLikeUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.ISessionRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.ClearSessionUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.DeleteFavoriteUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetFavoriteUsersUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserProfileUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserSessionUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.InsertFavoriteUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.LoginUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.LogoutUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.SaveUserSessionUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.SignInUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.UpdateUserProfileUseCase
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
    fun provideGetDraftUseCase(repo: IPostRepository): GetDraftUseCase = GetDraftUseCase(repo)

    @Provides
    fun provideSaveDraftUseCase(repo: IPostRepository): SaveDraftUseCase = SaveDraftUseCase(repo)

    @Provides
    fun provideDeleteDraftUseCase(repo: IPostRepository): DeleteDraftUseCase = DeleteDraftUseCase(repo)

    @Provides
    fun provideLoginUseCase(
        repo: IUserRepository,
        sessionManager: ISessionRepository,
    ): LoginUseCase = LoginUseCase(repo, sessionManager)

    @Provides
    fun provideClearSessionUseCase(sessionManager: ISessionRepository): ClearSessionUseCase = ClearSessionUseCase(sessionManager)

    @Provides
    fun provideGetUserSessionUseCase(sessionManager: ISessionRepository): GetUserSessionUseCase = GetUserSessionUseCase(sessionManager)

    @Provides
    fun provideSaveUserSessionUseCase(sessionManager: ISessionRepository): SaveUserSessionUseCase = SaveUserSessionUseCase(sessionManager)

    @Provides
    fun provideGetUserProfileUseCase(repo: IUserRepository): GetUserProfileUseCase = GetUserProfileUseCase(repo)

    @Provides
    fun provideLogoutUseCase(sessionManager: ISessionRepository): LogoutUseCase = LogoutUseCase(sessionManager)

    @Provides
    fun provideSignInUseCase(
        repo: IUserRepository,
        sessionManager: ISessionRepository,
    ): SignInUseCase = SignInUseCase(repo, sessionManager)

    @Provides
    fun provideGetFavoriteUsersUseCase(repo: IUserRepository): GetFavoriteUsersUseCase = GetFavoriteUsersUseCase(repo)

    @Provides
    fun provideInsertFavoriteUserUseCase(repo: IUserRepository): InsertFavoriteUserUseCase = InsertFavoriteUserUseCase(repo)

    @Provides
    fun provideDeleteFavoriteUserUseCase(repo: IUserRepository): DeleteFavoriteUserUseCase = DeleteFavoriteUserUseCase(repo)

    @Provides
    fun provideToggleLikeUseCase(repo: IPostRepository): ToggleLikeUseCase = ToggleLikeUseCase(repo)

    @Provides
    fun provideUpdateUserProfileUseCase(
        repo: IUserRepository,
        sessionManager: ISessionRepository,
    ): UpdateUserProfileUseCase = UpdateUserProfileUseCase(repo, sessionManager)
}
