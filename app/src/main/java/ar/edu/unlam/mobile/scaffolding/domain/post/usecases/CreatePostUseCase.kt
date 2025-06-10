package ar.edu.unlam.mobile.scaffolding.domain.post.usecases

import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository

class CreatePostUseCase(
    private val repo: IPostRepository,
) {
    suspend operator fun invoke(message: String) = repo.createPost(message)
}
