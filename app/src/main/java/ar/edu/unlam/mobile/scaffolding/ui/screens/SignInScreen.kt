package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.ProfileUserForm
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        ProfileUserForm(
            header = "Registro de Usuario",
            user = null,
            isLoading = isLoading,
            onSaveChanges = { name, email, _, password ->
                isLoading = true
                viewModel.registerUser(
                    name = name,
                    email = email ?: "",
                    password = password ?: "",
                    onRegistrationSuccess = {
                        scope.launch {
                            navController.navigate("home") {
                                popUpTo("register") {
                                    inclusive = true
                                }
                            }
                            snackbarHostState.showSnackbar("Registro exitoso")
                        }
                    },
                    onError = { errorMessage ->
                        isLoading = false
                        scope.launch {
                            snackbarHostState.showSnackbar(errorMessage)
                        }
                    },
                )
            },
        )
    }
}
