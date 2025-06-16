package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController

@Composable
fun HandleRefresh(
    navController: NavController,
    onRefresh: () -> Unit,
) {
    val shouldRefreshFlow =
        navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("shouldRefresh", false)

    val shouldRefresh by shouldRefreshFlow
        ?.collectAsState(initial = false)
        ?: remember { mutableStateOf(false) }

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<Boolean>("shouldRefresh")
            onRefresh()
        }
    }
}
