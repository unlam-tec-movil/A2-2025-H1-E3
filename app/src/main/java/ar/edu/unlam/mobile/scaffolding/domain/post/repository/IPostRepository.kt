package ar.edu.unlam.mobile.scaffolding.domain.post.repository

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import kotlinx.coroutines.flow.Flow

interface IPostRepository {
    fun getPosts(): Flow<List<Post>>

    fun getQuotes(postId: Int): Flow<List<Post>>

    suspend fun createPost(post: Post)
}
