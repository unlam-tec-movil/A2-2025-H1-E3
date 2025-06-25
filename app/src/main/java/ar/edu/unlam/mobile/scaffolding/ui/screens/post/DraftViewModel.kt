package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Draft
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.DeleteDraftUseCase
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetDraftUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface DraftUIState {
    data class Success(
        val drafts: List<Draft>,
    ) : DraftUIState

    data object Loading : DraftUIState

    data class Error(
        val message: String,
    ) : DraftUIState
}

data class DraftState(
    val draftUIState: DraftUIState,
    val message: String = "",
)

@HiltViewModel
class DraftViewModel
    @Inject
    constructor(
        private val getDraftsUseCase: GetDraftUseCase,
        private val deleteDraftUseCase: DeleteDraftUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(DraftState(DraftUIState.Loading))
        val uiState = _uiState.asStateFlow()

        init {
            loadDrafts()
        }

        fun loadDrafts() {
            viewModelScope.launch {
                getDraftsUseCase()
                    .onStart {
                        _uiState.value = DraftState(DraftUIState.Loading)
                    }.catch {
                        _uiState.value =
                            DraftState(
                                DraftUIState.Error(
                                    "Error cargando los borradores",
                                ),
                            )
                    }.collect { drafts ->
                        _uiState.value = DraftState(DraftUIState.Success(drafts))
                    }
            }
        }

        fun deleteDraft(draft: Draft) {
            viewModelScope.launch {
                try {
                    deleteDraftUseCase(draft)
                } catch (e: Exception) {
                    _uiState.value = DraftState(DraftUIState.Error("Error al eliminar borrador: ${e.message}"))
                }
            }
        }
    }
