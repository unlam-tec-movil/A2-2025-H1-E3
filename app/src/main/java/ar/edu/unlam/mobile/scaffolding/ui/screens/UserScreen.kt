package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.Avatar
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.ProfileUserForm
import ar.edu.unlam.mobile.scaffolding.ui.components.StateButton

@Composable
fun UserScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState by userViewModel.uiState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CustomHeader(title = "Mi perfil", onBack = { navController.popBackStack() })
        },
        contentWindowInsets = WindowInsets.statusBars,
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Avatar(
                avatarUrl = uiState.avatarUrl,
                avatarSize = 110.dp,
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))
            Text("Hola! ${ uiState.name }")
            Spacer(modifier = Modifier.padding(top = 16.dp))
            ProfileUserForm(
                header = "Editar perfil",
                isEditing = true,
                name = uiState.name,
                avatarUrl = uiState.avatarUrl,
                password = uiState.password,
                confirmPassword = uiState.confirmPassword,
                nameError = uiState.nameError,
                passwordError = uiState.passwordError,
                confirmPasswordError = uiState.confirmPasswordError,
                onNameChange = { userViewModel.onNameChange(it) },
                onPasswordChange = { userViewModel.onPasswordChange(it) },
                onConfirmPasswordChange = { userViewModel.onConfirmPasswordChange(it) },
                onEmailChange = {},
                onAvatarUrlChange = { userViewModel.onAvatarUrlChange(it) },
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))
            StateButton(text = "Guardar cambios", isLoading = uiState.isLoading, onClick = {
                userViewModel.editUser(
                    onEditSuccess = {
                        navController.previousBackStackEntry?.savedStateHandle?.set("shouldRefresh", true)
                        navController.popBackStack()
                    },
                    onError = { Toast.makeText(context, it, Toast.LENGTH_LONG).show() },
                )
            })
        }
    }
}
