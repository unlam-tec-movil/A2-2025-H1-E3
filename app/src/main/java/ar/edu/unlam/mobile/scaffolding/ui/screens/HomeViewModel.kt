package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetPostsUseCase
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
                    }.catch { throwable ->
                        _uiState.value =
                            PostUIState(
                                FeedUIState.Error("Error cargando el feed: ${throwable.message}"),
                            )
                    }.collect { posts ->
                        _uiState.value = PostUIState(FeedUIState.Success(posts))
                    }
            }
        }
//        fun fetchPosts() {
//            viewModelScope.launch {
//                try {
//                    val posts = postService.fetchPosts()
//                    _uiState.value = PostUIState(FeedUIState.Success(posts))
//                } catch (exception: Exception) {
//                    _uiState.value = PostUIState(FeedUIState.Error("Error cargando el feed"))
//                }
//            }
//        }

// ESTO NO VA SON DATOS DE PRUEBA HASTA QUE ANDE LA API
//        private fun generarPostsMock(): List<Post> {
//            // delay(5000L)
//            return List(1000) { index ->
//                Post(
//                    id = index,
//                    date = "2025-05-${(1..30).random()}",
//                    liked = true,
//                    author = "Christian Olivera",
//                    parentId = 1,
//                    avatarUrl =
//                        "https://scontent.feze9-1.fna.fbcdn.net/v/t1.6435-1/106390814_10221562122258462_" +
//                            "2961036162880397966_n.jpg?stp=cp0_dst-jpg_s40x40_tt6&_nc_cat=" +
//                            "105&ccb=1-7&_nc_sid=e99d92&_nc_ohc=0HtuY864DMMQ7kNvwFO2DvH&_nc_oc=" +
//                            "Adl2X8g_xGAf4LlRe0VHEvL2y_6pFs6aTWJmAO5RHmUNU5LGD_UV4z-QgtCvxTP3fQ5_" +
//                            "AU6e_trSeGPnDwvJTU_X&_nc_zt=24&_nc_ht=scontent.feze9-1.fna&_nc_gid=" +
//                            "5L8gOKlyQdCznYHdLvkLDw&oh=00_AfKpgUYcXEAnwAJNA6ioggZeZzLSSXy" +
//                            "Bv2wAvIs29jCFEw&oe=68641D92",
//                    likes = 100,
//                    message =
//                        "Para los q intentan entender, a franco lo paran y sale atrás de " +
//                            "Sainz y el equipo le pide q intente pasarlo, el alpine en " +
//                            "recta no puede pasar nunca a un motor mercedes, resultado? " +
//                            "Derrite los neumáticos por el aire sucio y encima en su stint " +
//                            "más largo, el equipo ya pidió",
//                )
//            }.shuffled()
//        }

//        fun fetchPosts() {
//            viewModelScope.launch {
//                try {
//                    val posts = generarPostsMock()
//                    _uiState.value = PostUIState(FeedUIState.Success(posts))
//                } catch (exception: Exception) {
//                    Log.e("HomeViewModel", "Error fetching posts", exception)
//                    _uiState.value = PostUIState(FeedUIState.Error("Error"))
//                }
//            }
//        }
    }
