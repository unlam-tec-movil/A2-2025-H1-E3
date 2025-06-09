package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomHeader

@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel = hiltViewModel(),
    navController: NavController,
) {
    val uiState: CreatePostState by viewModel.uiState.collectAsState()
    var message by remember { mutableStateOf("") }

    when (val createPostState = uiState.createPostUiState) {
        is CreatePostUIState.Success -> {
            LaunchedEffect(Unit) {
                navController.popBackStack()
            }
        }
        is CreatePostUIState.Error -> {
            Toast.makeText(LocalContext.current, createPostState.message, Toast.LENGTH_SHORT).show()
        }
        is CreatePostUIState.SuccessDraft -> {
            Toast.makeText(LocalContext.current, "Se ha guardado el borrador", Toast.LENGTH_SHORT).show()
            LaunchedEffect(Unit) {
                navController.popBackStack()
            }
        }
        else -> {}
    }

    Scaffold(
        topBar = {
            CustomHeader("Crear Post") {
                navController.popBackStack()
            }
        },
        content = { padding ->

            Column(
                modifier =
                    Modifier
                        .padding(padding)
                        .padding(16.dp)
                        .fillMaxSize(),
            ) {
                OutlinedTextField(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                    value = message,
                    onValueChange = { message = it },
                    label = { Text("¿Que estas pensando?") },
                )

                Button(
                    onClick = {
                        viewModel.addPost(
                            message = message,
                            author = "",
                            avatarUrl = "",
                            isDraft = true,
                        )
                    },
                ) {
                    Text("Guardar Borrador")
                }

                Button(
                    onClick = {
                        viewModel.addPost(
                            message = message,
                            author = "",
                            avatarUrl = "",
                            isDraft = false,
                        )
                    },
                ) {
                    Text("Publicar")
                }
            }
        },
    )
}
