package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@Immutable
sealed interface CreatePostUIState {
    data object Idle : CreatePostUIState

    data class Success(
        val post: Post,
    ) : CreatePostUIState

    data class SuccessDraft(
        val post: Post,
    ) : CreatePostUIState

    data object Loading : CreatePostUIState

    data class Error(
        val message: String,
    ) : CreatePostUIState
}

data class CreatePostState(
    val createPostUiState: CreatePostUIState,
)

@HiltViewModel
class CreatePostViewModel
    @Inject
    constructor(
        private val createPostUseCase: CreatePostUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CreatePostState(CreatePostUIState.Idle))
        val uiState = _uiState.asStateFlow()

        private fun getCurrentDate(): String =
            SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
                Date(),
            )

        fun addPost(
            message: String,
            author: String,
            avatarUrl: String,
            isDraft: Boolean,
            parentId: Int? = null,
        ) {
            _uiState.value = CreatePostState(CreatePostUIState.Loading)
            viewModelScope.launch {
                try {
                    val newPost =
                        Post(
                            id = (0..999).random(),
                            author = author,
                            date = getCurrentDate(),
                            liked = false,
                            likes = 0,
                            message = message,
                            parentId = parentId ?: 0,
                            avatarUrl = avatarUrl,
                        )
                    if (!isDraft) {
                        createPostUseCase(newPost)
                        _uiState.value = CreatePostState(CreatePostUIState.Success(newPost))
                    } else {
                        _uiState.value = CreatePostState(CreatePostUIState.SuccessDraft(newPost))
                    }
                } catch (exception: Exception) {
                    _uiState.value = CreatePostState(CreatePostUIState.Error("No se pudo crear el post ${exception.message}"))
                }
            }
        }
    }
