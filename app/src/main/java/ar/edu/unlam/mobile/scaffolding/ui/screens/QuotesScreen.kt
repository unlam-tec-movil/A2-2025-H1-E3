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
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.Quotes

@Composable
fun QuotesScreen(
    postId: Int,
    modifier: Modifier = Modifier,
    viewModel: QuotesViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val uiState: QuotesState by viewModel.uiState.collectAsState()

    LaunchedEffect(postId) {
        viewModel.loadQuotes(postId)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValues ->
        when (val quotesState = uiState.quotesUiState) {
            // loading component
            is QuotesUIState.Loading -> {
                CircularProgressIndicator(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                )
            }

            // success component
            is QuotesUIState.Success -> {
                Quotes(
                    posts = quotesState.posts,
                    modifier = modifier.padding(paddingValues),
                    onBackClick = { navController.popBackStack() },
                )
            }

            // error component
            is QuotesUIState.Error -> {
                LaunchedEffect(quotesState.message) {
                    snackbarHostState.showSnackbar(
                        message = quotesState.message,
                        actionLabel = "Cerrar",
                    )
                }
            }
        }
    }
}
