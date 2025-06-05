package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.services.PostService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        private val postService: PostService,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(PostUIState(FeedUIState.Loading))
        val uiState = _uiState.asStateFlow()

        init {
            fetchPosts()
        }

        fun fetchPosts() {
            viewModelScope.launch {
                try {
                    val posts = postService.fetchPosts()
                    _uiState.value = PostUIState(FeedUIState.Success(posts))
                } catch (exception: Exception) {
                    Log.e("HomeViewModel", "Error cargando posts", exception)
                    _uiState.value = PostUIState(FeedUIState.Error("Error"))
                }
            }
        }
    }
