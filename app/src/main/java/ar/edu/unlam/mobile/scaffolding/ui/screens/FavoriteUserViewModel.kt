package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.DeleteFavoriteUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetFavoriteUsersUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.InsertFavoriteUserUseCase
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
    val favoriteUIState: FavoriteUIState,
)

@HiltViewModel
class FavoriteUsersViewModel
    @Inject
    constructor(
        private val getFavoriteUsersUseCase: GetFavoriteUsersUseCase,
        private val deleteFavoriteUserUseCase: DeleteFavoriteUserUseCase,
        private val insertFavoriteUserUseCase: InsertFavoriteUserUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(FavoriteState(FavoriteUIState.Loading))
        val uiState = _uiState.asStateFlow()

        init {
            getFavoriteUsers()
        }

        private fun getFavoriteUsers() {
            viewModelScope.launch {
                getFavoriteUsersUseCase()
                    .onStart {
                        _uiState.value = FavoriteState(FavoriteUIState.Loading)
                    }.catch {
                        _uiState.value =
                            FavoriteState(
                                FavoriteUIState.Error(
                                    "Error cargando los Favoritos",
                                ),
                            )
                    }.collect { favorites ->
                        _uiState.value = FavoriteState(FavoriteUIState.Success(favorites))
                    }
            }
        }

        fun offFavorite(user: User) {
            viewModelScope.launch {
                deleteFavoriteUserUseCase(user)
            }
        }

        fun toggleFavorite(post: Post) {
            viewModelScope.launch {
                val currentState = _uiState.value.favoriteUIState
                if (currentState is FavoriteUIState.Success) {
                    val favoriteUser = currentState.users.find { it.name == post.author }
                    if (favoriteUser != null) {
                        deleteFavoriteUserUseCase(favoriteUser)
                    } else {
                        // Crear el nuevo User solo si no estaba
                        val user =
                            User(
                                id = 0, // en la base es autoincremental
                                avatarUrl = post.avatarUrl,
                                name = post.author,
                                email = "",
                            )
                        insertFavoriteUserUseCase(user)
                    }
                }
            }
        }
    }
