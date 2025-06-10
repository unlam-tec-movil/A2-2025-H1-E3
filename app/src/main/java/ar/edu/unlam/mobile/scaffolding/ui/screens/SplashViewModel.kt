package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unlam.mobile.scaffolding.domain.user.usecases.IsUserLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    ) : ViewModel() {
        private val _startDestination = MutableStateFlow("login")
        val startDestination: StateFlow<String> = _startDestination

        init {
            viewModelScope.launch {
                val isUserLoggedIn = isUserLoggedInUseCase()
                Log.d("Token:", isUserLoggedInUseCase().toString())
                _startDestination.value = if (isUserLoggedIn) "home" else "login"
            }
        }
    }
