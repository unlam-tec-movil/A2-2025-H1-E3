package ar.edu.unlam.mobile.scaffolding.domain.post.usecases

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import kotlinx.coroutines.flow.Flow

class GetQuotesUseCase(
    private val postRepository: IPostRepository,
) {
    operator fun invoke(postId: Int): Flow<List<Post>> = postRepository.getQuotes(postId)
}
