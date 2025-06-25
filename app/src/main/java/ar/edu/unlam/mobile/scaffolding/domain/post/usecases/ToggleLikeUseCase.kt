package ar.edu.unlam.mobile.scaffolding.domain.post.usecases

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import kotlinx.coroutines.flow.Flow

class ToggleLikeUseCase(
    private val postRepository: IPostRepository,
) {
    operator fun invoke(
        postId: String,
        like: Boolean,
    ): Flow<Post> =
        if (like) {
            postRepository.addLike(postId)
        } else {
            postRepository.removeLike(postId)
        }
}
