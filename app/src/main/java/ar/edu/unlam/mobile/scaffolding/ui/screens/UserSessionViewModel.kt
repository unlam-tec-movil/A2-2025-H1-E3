package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserProfileUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.LogoutUseCase
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
            checkSession()
        }

        fun checkSession() {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    _user.value = getUserProfileUseCase()
                    _isAuthenticated.value = true
                } catch (e: Exception) {
                    _isAuthenticated.value = false
                } finally {
                    _isLoading.value = false
                }
            }
        }

        fun logout(onComplete: () -> Unit = {}) {
            viewModelScope.launch {
                logoutUseCase()
                _user.value = null
                _isAuthenticated.value = false
                onComplete()
            }
        }
    }
