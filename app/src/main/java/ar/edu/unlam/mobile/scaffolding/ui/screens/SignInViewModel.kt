package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SignInViewModel
    @Inject
    constructor(
        private val userRepository: IUserRepository,
    ) : ViewModel() {
        fun registerUser(
            name: String,
            email: String,
            password: String,
            onRegistrationSuccess: () -> Unit,
            onError: (String) -> Unit,
        ) {
            viewModelScope.launch {
                try {
                    userRepository.register(name, email, password)
                    onRegistrationSuccess()
                } catch (e: IOException) {
                    onError(e.message ?: "error desconocido")
                }
            }
        }
    }
