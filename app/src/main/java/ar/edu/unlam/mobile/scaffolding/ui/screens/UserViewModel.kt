package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.GetUserSessionUseCase
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.UpdateUserProfileUseCase
import ar.edu.unlam.mobile.scaffolding.utils.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditUserState(
    val name: String = "",
    val avatarUrl: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val nameError: String? = null,
    val avatarUrlError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val showErrors: Boolean = false,
)

@HiltViewModel
class UserViewModel
    @Inject
    constructor(
        private val getUserSessionUseCase: GetUserSessionUseCase,
        private val updateUserProfileUseCase: UpdateUserProfileUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(EditUserState())
        val uiState: StateFlow<EditUserState> = _uiState

        init {
            loadUserData()
        }

        private fun loadUserData() {
            viewModelScope.launch {
                getUserSessionUseCase().collectLatest { user ->
                    user?.let {
                        _uiState.value =
                            EditUserState(
                                name = it.name.orEmpty(),
                                avatarUrl = it.avatarUrl.orEmpty(),
                            )
                    }
                }
            }
        }

        fun onNameChange(name: String) {
            _uiState.update { it.copy(name = name) }
        }

        fun onPasswordChange(password: String) {
            _uiState.update { it.copy(password = password) }
        }

        fun onConfirmPasswordChange(confirmPassword: String) {
            _uiState.update { it.copy(confirmPassword = confirmPassword) }
        }

        fun onAvatarUrlChange(avatarUrl: String) {
            _uiState.update { it.copy(avatarUrl = avatarUrl) }
        }

        private fun setLoading(isLoading: Boolean) {
            _uiState.update { it.copy(isLoading = isLoading) }
        }

        private fun verifyEditUser() {
            val nameValid = ValidationUtils.isNameValid(uiState.value.name)
            val passwordValid = ValidationUtils.isPasswordValid(uiState.value.password)
            val confirmPasswordValid =
                ValidationUtils.doPasswordsMatch(uiState.value.password, uiState.value.confirmPassword)
            val avatarUrlValid = (uiState.value.avatarUrl.isNotBlank())
            _uiState.update {
                it.copy(
                    nameError = if (!nameValid) "El nombre debe tener al menos 3 caracteres" else null,
                    passwordError = if (!passwordValid) "La contraseña debe tener al menos 3 caracteres" else null,
                    confirmPasswordError = if (!confirmPasswordValid) "Las contraseñas no coinciden" else null,
                    avatarUrlError = if (!avatarUrlValid) "El campo no puede estar vacio" else null,
                )
            }
        }

        fun editUser(
            onEditSuccess: () -> Unit,
            onError: (String) -> Unit,
        ) {
            _uiState.update { it.copy(showErrors = true) }
            verifyEditUser()
            if (listOf(
                    _uiState.value.nameError,
                    _uiState.value.passwordError,
                    _uiState.value.confirmPasswordError,
                    _uiState.value.avatarUrlError,
                ).any { it != null }
            ) {
                return
            }
            viewModelScope.launch {
                setLoading(true)
                try {
                    updateUserProfileUseCase(
                        _uiState.value.name,
                        _uiState.value.avatarUrl,
                        _uiState.value.password,
                    )
                } catch (e: Exception) {
                    onError(e.message ?: "Error al editar el usuario")
                } finally {
                    setLoading(false)
                    onEditSuccess()
                }
            }
        }
    }
