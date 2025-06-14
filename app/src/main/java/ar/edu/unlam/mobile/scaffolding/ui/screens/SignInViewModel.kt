package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.SignInUseCase
import ar.edu.unlam.mobile.scaffolding.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

data class SignInState(
    val name: String = "",
    val email: String = "",
    val avatarUrl: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val showErrors: Boolean = false,
)

@HiltViewModel
class SignInViewModel
    @Inject
    constructor(
        private val signInUseCase: SignInUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(SignInState())
        val uiState: StateFlow<SignInState> = _uiState

        fun onNameChange(name: String) {
            _uiState.update { it.copy(name = name) }
        }

        fun onEmailChange(email: String) {
            _uiState.update { it.copy(email = email) }
        }

        fun onPasswordChange(password: String) {
            _uiState.update { it.copy(password = password) }
        }

        fun onConfirmPasswordChange(confirmPassword: String) {
            _uiState.update { it.copy(confirmPassword = confirmPassword) }
        }

        private fun setLoading(isLoading: Boolean) {
            _uiState.update { it.copy(isLoading = isLoading) }
        }

        private fun verifySignIn() {
            val nameValid = ValidationUtils.isNameValid(uiState.value.name)
            val emailValid = ValidationUtils.isEmailValid(uiState.value.email)
            val passwordValid = ValidationUtils.isPasswordValid(uiState.value.password)
            val confirmPasswordValid =
                ValidationUtils.doPasswordsMatch(uiState.value.password, uiState.value.confirmPassword)
            _uiState.update {
                it.copy(
                    nameError = if (!nameValid) "El nombre debe tener al menos 3 caracteres" else null,
                    emailError = if (!emailValid) "El formato del email es incorrecto" else null,
                    passwordError = if (!passwordValid) "La contraseña debe tener al menos 3 caracteres" else null,
                    confirmPasswordError = if (!confirmPasswordValid) "Las contraseñas no coinciden" else null,
                )
            }
        }

        fun registerUser(
            onRegistrationSuccess: () -> Unit,
            onError: (String) -> Unit,
        ) {
            _uiState.update { it.copy(showErrors = true) }
            verifySignIn()
            if (_uiState.value.nameError != null ||
                _uiState.value.emailError != null ||
                _uiState.value.passwordError != null ||
                _uiState.value.confirmPasswordError != null
            ) {
                return
            }

            viewModelScope.launch {
                setLoading(true)
                try {
                    signInUseCase(
                        uiState.value.name,
                        uiState.value.email,
                        uiState.value.password,
                    )
                    onRegistrationSuccess()
                } catch (e: IOException) {
                    onError(e.message ?: "error desconocido")
                } finally {
                    setLoading(false)
                }
            }
        }
    }
