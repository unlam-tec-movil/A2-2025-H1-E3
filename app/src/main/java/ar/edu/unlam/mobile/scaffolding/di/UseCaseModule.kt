package ar.edu.unlam.mobile.scaffolding.di

import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreatePostUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreateReplyUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetPostsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetQuotesUseCase
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
}
