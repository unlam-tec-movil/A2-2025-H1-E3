package ar.edu.unlam.mobile.scaffolding.domain.post.usecases

import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository

class ToggleLikeUseCase(
    private val postRepository: IPostRepository,
) {
    suspend operator fun invoke(
        postId: String,
        like: Boolean,
    ) {
        if (like) {
            postRepository.addLike(postId)
        } else {
            postRepository.removeLike(postId)
        }
    }
}
