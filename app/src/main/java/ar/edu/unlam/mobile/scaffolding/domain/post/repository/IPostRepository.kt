package ar.edu.unlam.mobile.scaffolding.domain.post.repository

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import kotlinx.coroutines.flow.Flow

interface IPostRepository {
    suspend fun getPosts(): Flow<List<Post>>

    suspend fun createPost(post: Post)
}
