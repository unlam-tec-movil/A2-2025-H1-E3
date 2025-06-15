package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreatePostUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreateReplyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface CreatePostUIState {
    data object Idle : CreatePostUIState

    data class Success(
        val message: String,
    ) : CreatePostUIState

    data class SuccessDraft(
        val message: String,
    ) : CreatePostUIState

    data object Loading : CreatePostUIState

    data class Error(
        val message: String,
    ) : CreatePostUIState
}

data class CreatePostState(
    val createPostUiState: CreatePostUIState,
    val message: String = "",
)

@HiltViewModel
class CreatePostViewModel
    @Inject
    constructor(
        private val createPostUseCase: CreatePostUseCase,
        private val createReplyUseCase: CreateReplyUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CreatePostState(CreatePostUIState.Idle))
        val uiState = _uiState.asStateFlow()

        fun onMessageChange(newMessage: String) {
            _uiState.update {
                it.copy(message = newMessage)
            }
        }

        fun addPost(
            message: String,
            isDraft: Boolean,
            parentId: Int? = null,
        ) {
            _uiState.value = CreatePostState(CreatePostUIState.Loading)
            viewModelScope.launch {
                try {
                    when {
                        isDraft -> {
                            // llamar aca al metodo para guardar el borrador
                            _uiState.value =
                                CreatePostState(
                                    createPostUiState = CreatePostUIState.SuccessDraft(message),
                                    message = message,
                                )
                        }
                        parentId != null -> {
                            createReplyUseCase(parentId, message)
                            _uiState.value =
                                CreatePostState(
                                    createPostUiState = CreatePostUIState.Success(message),
                                    message = message,
                                )
                        }
                        else -> {
                            createPostUseCase(message)
                            _uiState.value =
                                CreatePostState(
                                    createPostUiState = CreatePostUIState.Success(message),
                                    message = message,
                                )
                        }
                    }
                } catch (exception: Exception) {
                    _uiState.value =
                        CreatePostState(
                            createPostUiState = CreatePostUIState.Error("No se pudo crear el post ${exception.message}"),
                            message = message,
                        )
                }
            }
        }
    }
