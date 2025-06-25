package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Draft
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreatePostUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.CreateReplyUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.SaveDraftUseCase
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
        private val saveDraftUseCase: SaveDraftUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(CreatePostState(CreatePostUIState.Idle))
        val uiState = _uiState.asStateFlow()

        fun onMessageChange(newMessage: String) {
            _uiState.update {
                it.copy(message = newMessage)
            }
        }

        fun addPost(
            isDraft: Boolean,
            parentId: Int? = null,
        ) {
            _uiState.update { it.copy(createPostUiState = CreatePostUIState.Loading) }
            viewModelScope.launch {
                val currentMessage = _uiState.value.message
                try {
                    when {
                        isDraft -> {
                            // llamar aca al metodo para guardar el borrador
                            val draft =
                                Draft(
                                    id = 0,
                                    message = currentMessage,
                                )
                            saveDraftUseCase(draft)

                            _uiState.update {
                                it.copy(
                                    createPostUiState = CreatePostUIState.SuccessDraft(currentMessage),
                                )
                            }
                        }
                        parentId != null -> {
                            createReplyUseCase(parentId, currentMessage)
                            _uiState.update {
                                it.copy(
                                    createPostUiState = CreatePostUIState.Success(currentMessage),
                                )
                            }
                        }
                        else -> {
                            createPostUseCase(currentMessage)
                            _uiState.update {
                                it.copy(
                                    createPostUiState = CreatePostUIState.Success(currentMessage),
                                )
                            }
                        }
                    }
                } catch (exception: Exception) {
                    _uiState.update {
                        it.copy(
                            createPostUiState = CreatePostUIState.Error("No se pudo crear el post ${exception.message}"),
                        )
                    }
                }
            }
        }

        fun resetState() {
            _uiState.update {
                it.copy(createPostUiState = CreatePostUIState.Idle)
            }
        }
    }
