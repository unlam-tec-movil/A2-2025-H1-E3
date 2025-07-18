package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.Favorites

@Composable
fun FavUsersScreen(
    viewModel: FavoriteUsersViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val uiState: FavoriteState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        when (val userState = uiState.favoriteUIState) {
            // loading component
            is FavoriteUIState.Loading -> {
                CircularProgressIndicator(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),
                )
            }

            // success component
            is FavoriteUIState.Success -> {
                Favorites(
                    users = userState.users,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    modifier = modifier.padding(paddingValues),
                    onFollowClick = { user -> viewModel.offFavorite(user) },
                )
            }

            // error component
            is FavoriteUIState.Error -> {
                LaunchedEffect(userState.message) {
                    snackbarHostState.showSnackbar(
                        message = userState.message,
                        actionLabel = "Cerrar",
                    )
                }
            }
        }
    }
}
