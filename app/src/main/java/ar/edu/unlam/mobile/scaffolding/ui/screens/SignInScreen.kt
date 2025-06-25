package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.ProfileUserForm
import ar.edu.unlam.mobile.scaffolding.ui.components.StateButton

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onSignInSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val context = LocalContext.current
    val uiState: SignInState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(modifier = Modifier.weight(.5f))
        // Logo de la app
        Image(
            painter = painterResource(id = R.drawable.logo_circle),
            contentDescription = "Logo de la app",
            modifier =
                Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp),
        )
        Text(
            text = "X Clone",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.weight(.5f))
        ProfileUserForm(
            header = "Registro de Usuario",
            isEditing = false,
            name = uiState.name,
            email = uiState.email,
            password = uiState.password,
            confirmPassword = uiState.confirmPassword,
            nameError = uiState.nameError,
            emailError = uiState.emailError,
            passwordError = uiState.passwordError,
            confirmPasswordError = uiState.confirmPasswordError,
            onNameChange = { viewModel.onNameChange(it) },
            onEmailChange = { viewModel.onEmailChange(it) },
            onPasswordChange = { viewModel.onPasswordChange(it) },
            onConfirmPasswordChange = { viewModel.onConfirmPasswordChange(it) },
            onAvatarUrlChange = {},
        )
        Spacer(Modifier.height(16.dp))
        StateButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Registrarse",
            isLoading = uiState.isLoading,
            onClick = {
                viewModel.registerUser(
                    onRegistrationSuccess = onSignInSuccess,
                    onError = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() },
                )
            },
        )
        Spacer(modifier = Modifier.weight(1.5f))
        TextButton(onClick = onNavigateToLogin) { Text(text = "¿Ya tienes una cuenta? Inicia sesión") }
    }
}
