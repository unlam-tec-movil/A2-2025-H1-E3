package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.LoginUseCase
import ar.edu.unlam.mobile.scaffolding.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false, // propiedad para el CircularProgress
    val showFieldErrors: Boolean = false,
)

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val loginUseCase: LoginUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(LoginState())
        val uiState: StateFlow<LoginState> = _uiState

        fun onEmailChange(email: String) {
            _uiState.update {
                it.copy(email = email)
            }
        }

        fun onPasswordChange(password: String) {
            _uiState.update {
                it.copy(password = password)
            }
        }

        private fun setLoading(isLoading: Boolean) {
            _uiState.update { it.copy(isLoading = isLoading) }
        }

        fun verifyLogin() {
            if (!_uiState.value.showFieldErrors) return // no mostrar los errores todavía
            // verificar si los campos son válidos
            val emailValid = ValidationUtils.isEmailValid(_uiState.value.email)
            val passwordValid = ValidationUtils.isPasswordValid(_uiState.value.password)
            _uiState.update {
                it.copy(
                    emailError = if (!emailValid) "El formato del email es incorrecto" else null,
                    passwordError = if (!passwordValid) "La contraseña debe ser mayor a 3 caracteres" else null,
                )
            }
        }

        fun login(
            onSuccess: () -> Unit,
            onError: (String) -> Unit,
        ) {
            _uiState.update { it.copy(showFieldErrors = true) } // activar errores
            verifyLogin()

            val hasErrors = _uiState.value.emailError != null || _uiState.value.passwordError != null
            if (hasErrors) return // no seguir con login si hay errores

            viewModelScope.launch {
                setLoading(true)
                try {
                    loginUseCase(_uiState.value.email, _uiState.value.password)
                    onSuccess()
                } catch (e: Exception) {
                    onError(e.message ?: "Error desconocido")
                } finally {
                    setLoading(false)
                }
            }
        }
    }
