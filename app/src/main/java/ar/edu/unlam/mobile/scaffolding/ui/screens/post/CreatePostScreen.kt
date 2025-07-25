package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Draft
import ar.edu.unlam.mobile.scaffolding.ui.components.Avatar
import ar.edu.unlam.mobile.scaffolding.ui.components.QuoteCard
import ar.edu.unlam.mobile.scaffolding.ui.screens.FeedUIState
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserSessionViewModel
import ar.edu.unlam.mobile.scaffolding.utils.decode

@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel = hiltViewModel(),
    userSessionViewModel: UserSessionViewModel = hiltViewModel(),
    navController: NavController,
    parentId: Int?,
    draftMessage: String?,
    draftId: Int?,
) {
    val uiState: CreatePostState by viewModel.uiState.collectAsState()

    // Datos del usuario para mostrar
    val user by userSessionViewModel.user.collectAsState()
    val context = LocalContext.current

    // Buscamos el post original
    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeUiState by homeViewModel.uiState.collectAsState()
    val quotedPost = (homeUiState.feedUiState as? FeedUIState.Success)?.posts?.find { it.id == parentId }

    // Poner el foco en el input del mensaje a redactar
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.resetState()
        draftMessage?.let { viewModel.onMessageChange(it.decode()) }
        focusRequester.requestFocus()
    }

    val draftViewModel: DraftViewModel = hiltViewModel()

    when (val createPostState = uiState.createPostUiState) {
        is CreatePostUIState.Success -> {
            draftId?.let {
                draftViewModel.deleteDraft(Draft(id = draftId, message = ""))
                navController.popBackStack()
            }
            navController.previousBackStackEntry?.savedStateHandle?.set("shouldRefresh", true)
            navController.popBackStack()
        }
        is CreatePostUIState.Error -> {
            Toast.makeText(context, createPostState.message, Toast.LENGTH_SHORT).show()
        }
        is CreatePostUIState.SuccessDraft -> {
            Toast.makeText(context, "Se ha guardado el borrador", Toast.LENGTH_SHORT).show()
            LaunchedEffect(createPostState) {
                navController.popBackStack()
            }
        }
        else -> {}
    }

    Scaffold(
        topBar = {
            CreatePostTopBar(
                onBackClick = {
                    navController.popBackStack()
                },
                // Accion de Publicar
                onPostClick = {
                    viewModel.addPost(isDraft = false, parentId = parentId)
                },
                enabled = uiState.message.isNotBlank() && uiState.message.length <= 280,
            )
        },
        content = { padding ->
            Column(
                modifier =
                    Modifier
                        .padding(padding)
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
            ) {
                // Input para redactar el mensaje
                PostTextFieldSection(
                    avatarUrl = user?.avatarUrl,
                    message = uiState.message,
                    onMessageChange = viewModel::onMessageChange,
                    focusRequester = focusRequester,
                )

                // Indicador de cantidad de caracteres
                PostLengthIndicator(length = uiState.message.length)

                // Mostrar mensaje citado si existe
                if (quotedPost != null) {
                    Text(
                        text = "Respondiendo a:",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    QuoteCard(post = quotedPost)
                }

                Spacer(modifier = Modifier.weight(1f))

                // Acciçon de guardar como borrador
                PostDraftButton(
                    enabled = uiState.message.isNotBlank() && uiState.message.length <= 280,
                    onClick = {
                        viewModel.addPost(isDraft = true)
                    },
                )
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostTopBar(
    onBackClick: () -> Unit,
    onPostClick: () -> Unit,
    enabled: Boolean,
) {
    TopAppBar(
        title = { Text("Crear post") },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Atrás")
            }
        },
        actions = {
            Button(
                onClick = onPostClick,
                enabled = enabled,
                shape = RoundedCornerShape(20.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                modifier = Modifier.padding(end = 8.dp),
            ) {
                Text("Publicar")
            }
        },
    )
}

@Composable
fun PostTextFieldSection(
    avatarUrl: String?,
    message: String,
    onMessageChange: (String) -> Unit,
    focusRequester: FocusRequester,
) {
    Row(modifier = Modifier.padding(top = 16.dp)) {
        Avatar(
            modifier = Modifier.padding(end = 8.dp),
            avatarUrl = avatarUrl,
            avatarSize = 38.dp,
        )
        OutlinedTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .focusRequester(focusRequester),
            value = message,
            onValueChange = onMessageChange,
            placeholder = { Text("¿Qué está pasando?") },
            textStyle = MaterialTheme.typography.bodyLarge,
            maxLines = 10,
            shape = RoundedCornerShape(12.dp),
            colors =
                OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary,
                ),
        )
    }
}

@Composable
fun PostLengthIndicator(length: Int) {
    Text(
        text = "$length/280",
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(end = 8.dp, top = 4.dp),
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.labelSmall,
        color = if (length > 280) Color.Red else Color.Gray,
    )
}

@Composable
fun PostDraftButton(
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.outlinedButtonColors(),
        ) {
            Text("Guardar borrador")
        }
    }
}
