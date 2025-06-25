package ar.edu.unlam.mobile.scaffolding.data.repositories

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.ApiService
import ar.edu.unlam.mobile.scaffolding.data.mappers.toDomain
import ar.edu.unlam.mobile.scaffolding.data.models.CreatePostRequestDto
import ar.edu.unlam.mobile.scaffolding.data.models.ReplyRequestDto
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
// Documentacion de Flow (Tambien ver dispositiva de clase)
// https://developer.android.com/kotlin/flow?hl=es-419
// Usamos Flow porque nos ofrece varias ventajas frente a una simple llamada suspend que devuelva una lista estática:
// 1. Reactividad
// Un Flow<T> representa una secuencia asíncrona de valores que van llegando con el tiempo.
// Si más adelante tu fuente de datos (por ejemplo, un cache local o un push del servidor) emite
// nuevos posts, tu ViewModel puede seguir recibiendo esas actualizaciones sin volver
// a pedir ttodo manualmente.

// 2. Operadores Funcionales
// Con Flow podés aplicar operadores como map, filter, debounce, retry, etc.,
// directamente en el nivel de dominio o de repositorio, antes de que la UI los consuma.
// Eso te da un pipeline muy flexible para transformar o filtrar datos.

// 3. Control de Contexto y Concurrencia
// flowOn(Dispatchers.IO) te permite mover ttodo el bloque de emisión (la llamada a la API)
// a un hilo de I/O, sin “ensuciar” tu lógica de negocio con detalles de concurrencia.
// Automáticamente respeta cancelaciones: si cierras la pantalla o cancelás la corrutina,
// la recolección del flujo se detiene.

// 4. Cold Streams (Flujos Fríos)
// Un Flow es “frío”: no hace nada hasta que alguien lo colecciona.
// Eso significa que podés definir tu Use Case para entregar un Flow, y cada consumidor
// decidirá cuándo y cuántas veces iniciarlo.

// 5. Integración con StateFlow / SharedFlow
// En tu ViewModel, al convertir el Flow<List<Post>> en un StateFlow (o exponerlo directamente),
// mantenés un estado observable que la UI puede suscribirse y
// actualizarse automáticamente cada vez que cambia.

    override fun getPosts(): Flow<List<Post>> =
        flow {
            val response = apiService.getFeed()
            emit(response.map { it.toDomain() })
        }.flowOn(Dispatchers.IO)

    override fun getQuotes(postId: Int): Flow<List<Post>> =
        flow {
            val response = apiService.getQuotes(postId)
            emit(response.map { it.toDomain() })
        }.flowOn(Dispatchers.IO)

    override suspend fun createPost(message: String) {
        try {
            val dto = CreatePostRequestDto(message)
            val response = apiService.createPost(dto)

            if (!response.isSuccessful) {
                throw Exception(response.code().toString())
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createReply(
        postId: Int,
        message: String,
    ) {
        try {
            val dto = ReplyRequestDto(message)
            val response = apiService.createReply(postId, dto)

            if (!response.isSuccessful) {
                throw Exception(response.code().toString())
            }
        } catch (e: HttpException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override fun addLike(postId: String): Flow<Post> =
        flow {
            val response = apiService.addLike(postId)
            emit(response.toDomain())
        }.flowOn(Dispatchers.IO)

    override fun removeLike(postId: String): Flow<Post> =
        flow {
            val response = apiService.removeLike(postId)
            emit(response.toDomain())
        }.flowOn(Dispatchers.IO)
}
