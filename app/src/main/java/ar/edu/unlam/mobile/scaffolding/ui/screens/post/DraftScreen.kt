package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Draft
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomHeader
import ar.edu.unlam.mobile.scaffolding.utils.encode

@Composable
fun DraftScreen(
    navController: NavController,
    viewModel: DraftViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadDrafts()
    }

    Scaffold(
        topBar = {
            CustomHeader(title = "Borradores", onBack = { navController.popBackStack() })
        },
    ) { innerPadding ->
        when (val state = uiState.draftUIState) {
            is DraftUIState.Loading -> {
                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is DraftUIState.Success -> {
                if (state.drafts.isEmpty()) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("No hay borradores guardados.")
                    }
                } else {
                    LazyColumn(
                        modifier =
                            Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                                .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(state.drafts) { draft ->
                            DraftItem(
                                draft = draft,
                                onDelete = { viewModel.deleteDraft(draft) },
                                onPublish = { draftId, draftMessage ->
                                    navController.navigate(
                                        "addPost?replyTo={replyTo}&&draftMessage=" +
                                            "${draftMessage.encode()}&draftId=$draftId",
                                    )
                                },
                            )
                        }
                    }
                }
            }

            is DraftUIState.Error -> {
                Text(
                    text = state.message,
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .padding(16.dp),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Composable
fun DraftItem(
    draft: Draft,
    onDelete: () -> Unit,
    onPublish: (Int, String) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
    ) {
        Text(
            text = draft.message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                onClick = { onPublish(draft.id, draft.message) },
                modifier = Modifier.weight(1f),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Icon(Icons.Default.Publish, contentDescription = "Publicar")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Publicar")
            }

            Button(
                onClick = onDelete,
                modifier = Modifier.weight(1f),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError,
                    ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Eliminar")
            }
        }

        HorizontalDivider(modifier = Modifier.padding(top = 12.dp))
    }
}
