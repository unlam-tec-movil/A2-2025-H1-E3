package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepository(
    private val apiService: ApiService,
) : IPostRepository {
    override suspend fun getPosts(): Flow<List<Post>> =
        flow {
            val response = apiService.getFeed()
            emit(response)
        }.flowOn(Dispatchers.IO)
}
