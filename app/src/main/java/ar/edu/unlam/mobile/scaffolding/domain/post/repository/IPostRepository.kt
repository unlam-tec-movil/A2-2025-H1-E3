package ar.edu.unlam.mobile.scaffolding.domain.post.repository

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Draft
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import kotlinx.coroutines.flow.Flow

interface IPostRepository {
    fun getPosts(): Flow<List<Post>>

    fun getQuotes(postId: Int): Flow<List<Post>>

    suspend fun createPost(message: String)

    suspend fun createReply(
        postId: Int,
        message: String,
    )

    suspend fun addLike(postId: String)

    suspend fun removeLike(postId: String)

    fun getDrafts(): Flow<List<Draft>>

    suspend fun saveDraft(draft: Draft)

    suspend fun removeDraft(draft: Draft)
}
