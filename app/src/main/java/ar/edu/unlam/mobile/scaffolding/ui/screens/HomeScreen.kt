package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.components.Feed

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
) {
    val uiState: PostUIState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        when (val postState = uiState.feedUiState) {
            // loading component
            is FeedUIState.Loading -> {
                CircularProgressIndicator(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                )
            }

            // success component
            is FeedUIState.Success -> {
                Feed(posts = postState.posts, modifier = modifier.padding(paddingValues))
            }

            // error component
            is FeedUIState.Error -> {
                // https://developer.android.com/develop/ui/compose/side-effects?hl=es-419
                // LaunchedEffect: Ejecuta funciones de suspensión en el alcance de un elemento componible.
                // Cuando necesites realizar cambios en el estado de la app, ej para mostrar una Snackbar
                LaunchedEffect(postState.message) {
                    snackbarHostState.showSnackbar(
                        message = postState.message,
                        actionLabel = "Cerrar",
                    )
                }
            }
        }
    }
}
