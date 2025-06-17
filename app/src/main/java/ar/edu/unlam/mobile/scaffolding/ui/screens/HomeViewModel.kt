package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetPostsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.DeleteFavoriteUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetFavoriteUsersUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserProfileUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.InsertFavoriteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface FeedUIState {
    data class Success(
        val posts: List<Post>,
        val repliesMap: Map<Int, Int>,
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
        private val getUserProfileUseCase: GetUserProfileUseCase,
        private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
        private val insertFavoriteUserUseCase: InsertFavoriteUserUseCase,
        private val deleteFavoriteUserUseCase: DeleteFavoriteUserUseCase,
    ) : ViewModel() {
        // Mutable State Flow contiene un objeto de estado mutable. Simplifica la operación de
        // actualización de información y de manejo de estados de una aplicación: Cargando, Error, Éxito
        // (https://developer.android.com/kotlin/flow/stateflow-and-sharedflow)

        // _Ui State es el estado general del view model.
        private val _uiState = MutableStateFlow(PostUIState(FeedUIState.Loading))

        // UIState expone el estado anterior como un Flujo de Estado de solo lectura.
        // Esto impide que se pueda modificar el estado desde fuera del ViewModel.
        val uiState = _uiState.asStateFlow()

        // Lista de favoritos en memoria
        private val _favoriteUsers = MutableStateFlow<List<User>>(emptyList())
        val favoriteUsers: StateFlow<List<User>> = _favoriteUsers

        init {
            fetchPosts()
            getFavoriteUsers()
        }

        fun fetchPosts() {
            viewModelScope.launch {
                getPostsUseCase()
                    .onStart {
                        _uiState.value =
                            _uiState.value.copy(
                                feedUiState = FeedUIState.Loading,
                            )
                    }.catch { throwable ->
                        _uiState.value =
                            _uiState.value.copy(
                                // feedUiState = FeedUIState.Error("Error cargando el feed: ${throwable.message}"),
                                feedUiState =
                                    FeedUIState.Error(
                                        "Error cargando el feed: Verifique la conexión a internet e intente nuevamente",
                                    ),
                            )
                    }.collect { posts ->
                        // extra: creamos un map (Postid, CantidadReplyes) para mostrar en el feed
                        val repliesMap: Map<Int, Int> =
                            posts
                                .groupingBy { it.parentId }
                                .eachCount()
                        _uiState.value =
                            _uiState.value.copy(
                                feedUiState = FeedUIState.Success(posts = posts, repliesMap = repliesMap),
                            )
                    }
            }
        }

        private fun getFavoriteUsers() {
            viewModelScope.launch {
                getFavoriteUsersUseCase().collect { favorites ->
                    _favoriteUsers.value = favorites
                }
            }
        }

        fun toggleFavorite(post: Post) {
            // Crear el User a insertar/eliminar
            val user =
                User(
                    avatarUrl = post.avatarUrl,
                    name = post.author,
                    email = "",
                )

            viewModelScope.launch {
                val isAlreadyFavorite = favoriteUsers.value.any { it.name == post.author }

                if (isAlreadyFavorite) {
                    Log.d("HomeViewModel", "Eliminando usuario de favoritos: ${user.name}")
                    deleteFavoriteUserUseCase(user)
                } else {
                    Log.d("HomeViewModel", "Agregando usuario a favoritos: ${user.name}")
                    insertFavoriteUserUseCase(user)
                }
            }
        }

        fun isFavorite(author: String): Flow<Boolean> = favoriteUsers.map { list -> list.any { it.name == author } }
    }
