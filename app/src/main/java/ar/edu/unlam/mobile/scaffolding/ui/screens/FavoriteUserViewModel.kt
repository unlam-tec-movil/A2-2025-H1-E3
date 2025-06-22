package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetFavoriteUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FavoriteUIState {
    data class Success(
        val users: List<User>,
    ) : FavoriteUIState

    data object Loading : FavoriteUIState

    data class Error(
        val message: String,
    ) : FavoriteUIState
}

data class FavoriteState(
    val favoriteUIState: FavoriteUIState,
)

@HiltViewModel
class FavoriteUsersViewModel
    @Inject
    constructor(
        private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FavoriteState(FavoriteUIState.Loading))
        val uiState = _uiState.asStateFlow()

        private val _favoriteUsers = MutableStateFlow<List<User>>(emptyList())
        val favoriteUsers: StateFlow<List<User>> = _favoriteUsers

        init {
            getFavoriteUsers()
        }

        private fun getFavoriteUsers() {
            viewModelScope.launch {
                getFavoriteUsersUseCase().collect { favorites ->
                    _favoriteUsers.value = favorites
                }
            }
        }
    }
