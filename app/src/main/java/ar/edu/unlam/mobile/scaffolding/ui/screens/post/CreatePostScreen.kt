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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.Avatar
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserSessionViewModel
import ar.edu.unlam.mobile.scaffolding.utils.decode

@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel = hiltViewModel(),
    userSessionViewModel: UserSessionViewModel = hiltViewModel(),
    navController: NavController,
    backStackEntry: NavBackStackEntry,
) {
    val uiState: CreatePostState by viewModel.uiState.collectAsState()
    val user by userSessionViewModel.user.collectAsState()
    val context = LocalContext.current

    // Usuario al que estamos respondiendo el post
    val id = backStackEntry.arguments?.getInt("id")
    val author = backStackEntry.arguments?.getString("author")?.decode()
    val quotedMessage = backStackEntry.arguments?.getString("message")?.decode()

    // Poner el foco en el input
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    when (val createPostState = uiState.createPostUiState) {
        is CreatePostUIState.Success -> {
            navController.previousBackStackEntry?.savedStateHandle?.set("shouldRefresh", true)
            navController.popBackStack()
        }
        is CreatePostUIState.Error -> {
            Toast.makeText(context, createPostState.message, Toast.LENGTH_SHORT).show()
        }
        is CreatePostUIState.SuccessDraft -> {
            Toast.makeText(context, "Se ha guardado el borrador", Toast.LENGTH_SHORT).show()
            LaunchedEffect(Unit) {
                navController.popBackStack()
            }
        }
        else -> {}
    }

    Scaffold(
        topBar = {
            CreatePostTopBar(
                onBackClick = { navController.popBackStack() },
                onPostClick = {
                    viewModel.addPost(uiState.message, isDraft = false, parentId = id)
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
                PostTextFieldSection(
                    avatarUrl = user?.avatarUrl,
                    message = uiState.message,
                    onMessageChange = viewModel::onMessageChange,
                    focusRequester = focusRequester,
                )

                PostLengthIndicator(length = uiState.message.length)

                if (!quotedMessage.isNullOrBlank() && !author.isNullOrBlank()) {
                    QuotedMessageCard(author = author, message = quotedMessage)
                }

                Spacer(modifier = Modifier.weight(1f))

                PostDraftButton(
                    enabled = uiState.message.isNotBlank() && uiState.message.length <= 280,
                    onClick = {
                        viewModel.addPost(uiState.message, isDraft = true)
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
fun QuotedMessageCard(
    author: String,
    message: String,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Respondiendo a @$author", style = MaterialTheme.typography.labelMedium)
            Text(text = message, style = MaterialTheme.typography.bodyMedium)
        }
    }
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
