package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetPostsUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserProfileUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.LogoutUseCase
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
        val repliesMap: Map<Int, Int>,
    ) : FeedUIState

    data object Loading : FeedUIState

    data class Error(
        val message: String,
    ) : FeedUIState
}

data class PostUIState(
    val feedUiState: FeedUIState,
    val user: User? = null,
    val isUserLoading: Boolean = true,
    val userError: String? = null,
)

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val getPostsUseCase: GetPostsUseCase,
        private val getUserProfileUseCase: GetUserProfileUseCase,
        private val logoutUseCase: LogoutUseCase,
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
            fetchUserProfile()
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
                                feedUiState = FeedUIState.Error("Error cargando el feed: ${throwable.message}"),
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

        fun fetchUserProfile() {
            viewModelScope.launch {
                try {
                    val user = getUserProfileUseCase()
                    Log.d("Usuario:", user.toString())
                    _uiState.value = _uiState.value.copy(user = user, isUserLoading = false)
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            userError = e.message ?: "Error al cargar el perfil",
                            isUserLoading = false,
                        )
                }
            }
        }

        fun logout() {
            logoutUseCase()
        }
    }
