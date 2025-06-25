package ar.edu.unlam.mobile.scaffolding.domain.post.usecases

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Draft
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import kotlinx.coroutines.flow.Flow

class GetDraftUseCase(
    private val postRepository: IPostRepository,
) {
    operator fun invoke(): Flow<List<Draft>> = postRepository.getDrafts()
}
