package ar.edu.unlam.mobile.scaffolding.domain.post.usecases

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Draft
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository

class SaveDraftUseCase(
    private val postRepository: IPostRepository,
) {
    suspend operator fun invoke(draft: Draft) = postRepository.saveDraft(draft)
}
