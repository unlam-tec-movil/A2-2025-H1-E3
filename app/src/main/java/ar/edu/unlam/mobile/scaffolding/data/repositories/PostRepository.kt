package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import coil.network.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PostRepository(
    // Implementación para el repositorio de Post
    private val apiService: ApiService,
) : IPostRepository {
    // Documentacion de Flow (Ver dispositiva de clase)
    // https://developer.android.com/kotlin/flow?hl=es-419
    override suspend fun getPosts(): Flow<List<Post>> =
        flow {
            val response = apiService.getFeed()
            emit(response)
        }.flowOn(Dispatchers.IO)

    override suspend fun createPost(post: Post) {
        try {
            val response = apiService.createPost(post)

            if (!response.isSuccessful) {
                throw Exception(response.code().toString())
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}
