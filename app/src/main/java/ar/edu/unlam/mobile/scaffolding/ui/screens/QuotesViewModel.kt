package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.post.usecases.GetQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@Immutable
sealed interface QuotesUIState {
    data class Success(
        val posts: List<Post>,
    ) : QuotesUIState

    data object Loading : QuotesUIState

    data class Error(
        val message: String,
    ) : QuotesUIState
}

data class QuotesState(
    val quotesUiState: QuotesUIState,
)

@HiltViewModel
class QuotesViewModel
    @Inject
    constructor(
        private val getQuotesUseCase: GetQuotesUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(QuotesState(QuotesUIState.Loading))
        val uiState = _uiState.asStateFlow()

        fun loadQuotes(postId: Int) {
            viewModelScope.launch {
                getQuotesUseCase(postId)
                    .onStart {
                        _uiState.value = QuotesState(QuotesUIState.Loading)
                    }.catch { throwable ->
                        _uiState.value =
                            QuotesState(
                                // QuotesUIState.Error("Error cargando las citas: ${throwable.message}"),
                                QuotesUIState.Error(
                                    "Error cargando las respuestas: " +
                                        "Verifique la conexión a internet e intente nuevamente",
                                ),
                            )
                    }.collect { posts ->
                        _uiState.value = QuotesState(QuotesUIState.Success(posts))
                    }
            }
        }
    }
