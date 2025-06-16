package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.os.Message
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.data.repositories.UserRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
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
    val favoriteUIState: FavoriteUIState
)

@HiltViewModel
class FavoriteUsersViewModel
@Inject
constructor(
    private val getUsersUseCase: GetUsersUseCase,
    private val userRepository: IUserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavoriteState(FavoriteUIState.Loading))
    val uiState = _uiState.asStateFlow()

    fun loadFavorites(userId: Int) {
        viewModelScope.launch {
            getUsersUseCase(userId).onStart {
                _uiState.value = FavoriteState(FavoriteUIState.Loading)
            }.catch { throwable ->
                _uiState.value = FavoriteState(
                    FavoriteUIState.Error("Error cargando los favoritos: ${throwable.message}"),
                )
            }.collect { users ->
                    _uiState.value = FavoriteState(FavoriteUIState.Success(users))

                }
            }
        }
    }

