package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetPostsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.ToggleLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface FeedUIState {
    data class Success(
        val posts: List<Post>,
    ) : FeedUIState

    data object Loading : FeedUIState

    data class Error(
        val message: String,
    ) : FeedUIState
}

data class PostUIState(
    val feedUiState: FeedUIState,
)

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getPostsUseCase: GetPostsUseCase,
        private val toggleLikeUseCase: ToggleLikeUseCase,
    ) : ViewModel() {
        // Mutable State Flow contiene un objeto de estado mutable. Simplifica la operación de
        // actualización de información y de manejo de estados de una aplicación: Cargando, Error, Éxito
        // (https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

        // _Ui State es el estado general del view model.
        private val _uiState = MutableStateFlow(PostUIState(FeedUIState.Loading))

        // UIState expone el estado anterior como un Flujo de Estado de solo lectura.
        // Esto impide que se pueda modificar el estado desde fuera del ViewModel.
        val uiState = _uiState.asStateFlow()

        init {
            fetchPosts()
        }

        fun fetchPosts() {
            viewModelScope.launch {
                getPostsUseCase()
                    .onStart {
                        _uiState.value = PostUIState(FeedUIState.Loading)
                    }.catch {
                        _uiState.value =
                            PostUIState(
                                FeedUIState.Error(
                                    "Error cargando el feed: Verifique la conexión a internet e intente nuevamente",
                                ),
                            )
                    }.collect { posts ->
                        _uiState.value = PostUIState(FeedUIState.Success(posts))
                    }
            }
        }

        // Con flow
//        fun toggleLike(post: Post) {
//            viewModelScope.launch {
//                toggleLikeUseCase(post.id.toString(), !post.liked) // alternamos el like
//                    .catch {
//                        _uiState.value = PostUIState(FeedUIState.Error("Error al dar like"))
//                    }.collect { updatedPost ->
//                        val currentState = _uiState.value.feedUiState
//                        if (currentState is FeedUIState.Success) {
//                            val updatedPosts =
//                                currentState.posts.map {
//                                    if (it.id == updatedPost.id) updatedPost else it
//                                }
//                            _uiState.value = PostUIState(FeedUIState.Success(updatedPosts))
//                        }
//                    }
//            }
//        }

        // Optimismo visual: mejora la experiencia del usuario al ver el cambio instantáneamente.
        // Reversión segura: si algo falla, se recupera el estado anterior.
        // Separación de lógica: lo visual (updateFeedState) está separado del uso de red (toggleLikeUseCase).
        fun toggleLike(post: Post) {
            // Obtiene el estado actual del feed, que puede ser Loading, Error o Success.
            val currentFeedState = _uiState.value.feedUiState

            // Solo seguimos si el estado es Success, porque eso significa que ya se cargaron los posts y podemos modificarlos.
            if (currentFeedState is FeedUIState.Success) {
                // Se obtiene la lista de posts actual como una lista mutable para poder modificarla.
                val currentPosts = currentFeedState.posts.toMutableList()

                // Buscamos la posición del post en la lista original.
                val index = currentPosts.indexOfFirst { it.id == post.id }

                // Si no lo encontramos (-1), terminamos la función (no hacemos nada).
                if (index == -1) return

                // Creamos una copia del post con el liked invertido (true → false, o viceversa),
                // y actualizamos la cantidad de likes.
                val updatedPost =
                    post.copy(
                        liked = !post.liked,
                        likes = if (post.liked) post.likes - 1 else post.likes + 1,
                    )

                // Reemplazamos el post original en la lista con su versión actualizada (liked cambiado)
                currentPosts[index] = updatedPost

                // Actualizamos el estado de la UI para que se vea el cambio en pantalla
                // de inmediato (optimismo visual).
                _uiState.value = PostUIState(FeedUIState.Success(currentPosts))

                // Lanza una corutina para Enviar el “like” (o “unlike”) al servidor mediante toggleLikeUseCase.
                viewModelScope.launch {
                    try {
                        toggleLikeUseCase(post.id.toString(), updatedPost.liked)
                    } catch (e: Exception) {
                        // Revertir en caso de error
                        currentPosts[index] = post
                        _uiState.value = PostUIState(FeedUIState.Success(currentPosts))
                    }
                }
            }
        }
    }
