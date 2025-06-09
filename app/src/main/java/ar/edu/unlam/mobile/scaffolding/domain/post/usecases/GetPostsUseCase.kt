package ar.edu.unlam.mobile.scaffolding.domain.post.usecases

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.repository.IPostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(
    private val postRepository: IPostRepository,
) {
    // El operador invoke en Kotlin permite que una instancia de clase se comporte como una función.
    // Es decir, podés llamar al objeto como si fuera una función, sin tener que escribir
    // explícitamente el nombre del méttodo.
    // Gracias al operador invoke, podés hacer esto en el ViewModel: getPostsUseCase()
    operator fun invoke(): Flow<List<Post>> = postRepository.getPosts()
}

// Utilizamos UseCase por si en el futuro queremos agregar parámetros de filtrado o paginación. EJ:
// class GetPostsUseCase(
//    private val postRepository: IPostRepository,
// ) {
//    operator fun invoke(onlyDrafts: Boolean = false): Flow<List<Post>> =
//        postRepository.getPosts().map { posts ->
//            if (onlyDrafts) posts.filter { it.isDraft } else posts
//        }
// }

// Así cada clase tiene una sola responsabilidad, y te permite mockear/componer casos de uso
// individualmente en tests.
