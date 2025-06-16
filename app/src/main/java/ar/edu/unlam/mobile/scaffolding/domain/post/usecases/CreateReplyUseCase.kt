package ar.edu.unlam.mobile.scaffolding.domain.post.usecases

import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository

class CreateReplyUseCase(
    private val repo: IPostRepository,
) {
    suspend operator fun invoke(
        postId: Int,
        message: String,
    ) = repo.createReply(postId, message)
}
