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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomTextField
import ar.edu.unlam.mobile.scaffolding.ui.components.StateButton

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
) {
    val context = LocalContext.current
    val uiState: LoginState by viewModel.uiState.collectAsStateWithLifecycle()

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
                    .padding(bottom = 24.dp),
        )

        Spacer(modifier = Modifier.weight(.5f))
        Text("Iniciar sesión", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = uiState.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = "Email",
            error = uiState.emailError,
            keyboardAction = ImeAction.Next,
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = uiState.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = "Contraseña",
            isPassword = true,
            error = uiState.passwordError,
            keyboardAction = ImeAction.Done,
        )

        Spacer(modifier = Modifier.height(16.dp))

        StateButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Iniciar sesión",
            isLoading = uiState.isLoading,
            onClick = {
                viewModel.login(
                    onSuccess = onLoginSuccess,
                    onError = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() },
                )
            },
        )
        Spacer(modifier = Modifier.weight(1.5f))
        TextButton(onClick = onNavigateToRegister) { Text(text = "¿No tienes cuenta? Registrate") }
    }
}
