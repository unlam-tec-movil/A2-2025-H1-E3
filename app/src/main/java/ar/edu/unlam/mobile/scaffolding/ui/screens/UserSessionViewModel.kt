package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.ClearSessionUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSessionViewModel
    @Inject
    constructor(
        getUserSessionUseCase: GetUserSessionUseCase,
        private val clearSessionUseCase: ClearSessionUseCase,
    ) : ViewModel() {
        val user: StateFlow<User?> =
            getUserSessionUseCase()
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

        val isAuthenticated: StateFlow<Boolean> =
            user
                .map { it != null }
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), false)

        fun logout() {
            viewModelScope.launch {
                clearSessionUseCase()
            }
        }
    }

// Usamos LocalUser como una forma global y reactiva de acceder al usuario logueado
// desde cualquier parte de la UI. Lo alimentamos con un StateFlow que proviene del dominio
// (GetUserSessionUseCase), y usamos stateIn(...) para transformarlo en algo observable desde
// Compose, con memoria del último valor y eficiencia en el ciclo de vida.

// .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
// Parametros:
// viewModelScope: El alcance de vida del StateFlow (se cancela con el ViewModel)
// SharingStarted.WhileSubscribed :Solo se mantiene activo mientras haya observadores
// 5000 : Si todos los observers se desconectan, espera 5 segundos antes de parar
// null: Valor inicial (antes de que el Flow emita algo)
