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
    // Actualizar la lista de post luego de crear uno nuevo
    // 1. Sacamos un StateFlow<Boolean> con un valor por defecto
    val shouldRefreshFlow =
        navController
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getStateFlow("shouldRefresh", false)

    // 2. Lo convertimos a State<Bool> con collectAsState
    val shouldRefresh by shouldRefreshFlow
        ?.collectAsState(initial = false)
        ?: remember { mutableStateOf(false) }

    // 3. Cuando cambie a true, lo limpiamos y refrescamos
    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.remove<Boolean>("shouldRefresh")
            onRefresh()
        }
    }
}
