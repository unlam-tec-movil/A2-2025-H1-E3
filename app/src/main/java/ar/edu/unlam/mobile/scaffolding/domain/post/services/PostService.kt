package ar.edu.unlam.mobile.scaffolding.domain.post.services

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PostService @Inject
constructor(
    private val postRepository: IPostRepository
) {
    suspend fun fetchPosts(): List<Post> = postRepository.getPosts().first()
}