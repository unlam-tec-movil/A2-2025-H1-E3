package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.ClearCachedUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetCachedUserUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserProfileUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.LogoutUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.SaveCachedUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserSessionViewModel
    @Inject
    constructor(
        private val logoutUseCase: LogoutUseCase,
        private val getUserProfileUseCase: GetUserProfileUseCase,
        private val getCachedUserUseCase: GetCachedUserUseCase,
        private val saveCachedUserUseCase: SaveCachedUserUseCase,
        private val clearCachedUserUseCase: ClearCachedUserUseCase,
    ) : ViewModel() {
        private val _user = MutableStateFlow<User?>(null)
        val user: StateFlow<User?> = _user.asStateFlow()

        private val _isAuthenticated = MutableStateFlow(false)
        val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

        private val _error = MutableStateFlow<String?>(null)
        val error: StateFlow<String?> = _error.asStateFlow()

        init {
            loadCachedUserAndUpdateSession()
        }

        private fun loadCachedUserAndUpdateSession() {
            viewModelScope.launch {
                // Paso 1: Cargar usuario cacheado (muestra rápido en UI)
                val cached = getCachedUserUseCase()
                _user.value = cached
                _isAuthenticated.value = cached != null

                // Paso 2: Validar sesión actual contra API
                checkSession()
            }
        }

        fun checkSession() {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val freshUser = getUserProfileUseCase()
                    _user.value = freshUser
                    _isAuthenticated.value = true
                    saveCachedUserUseCase(freshUser) // Actualiza el cache
                } catch (e: Exception) {
                    _isAuthenticated.value = false
                    _error.value = e.message
                } finally {
                    _isLoading.value = false
                }
            }
        }

        fun logout(onComplete: () -> Unit = {}) {
            viewModelScope.launch {
                logoutUseCase()
                clearCachedUserUseCase()
                _user.value = null
                _isAuthenticated.value = false
                onComplete()
            }
        }
    }
