package ar.edu.unlam.mobile.scaffolding.ui.screens.post

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                .padding(4.dp),
    ) {
        Text(
            text = draft.message,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
        ) {
            TextButton(
                onClick = { onPublish(draft.id, draft.message) },
            ) {
                Text(
                    text = "Publicar",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            TextButton(
                onClick = onDelete,
            ) {
                Text(
                    text = "Eliminar",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }

        HorizontalDivider(modifier = Modifier.padding(top = 6.dp))
//        HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
    }
}
