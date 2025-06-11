package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.ui.components.Feed
import ar.edu.unlam.mobile.scaffolding.ui.components.PostOptionsBottomSheet
import ar.edu.unlam.mobile.scaffolding.utils.encode
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navController: NavController,
) {
    val uiState: PostUIState by viewModel.uiState.collectAsState()

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
            viewModel.fetchPosts()
        }
    }

    // Estado del BottomSheet
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    var isSheetOpen by remember { mutableStateOf(false) }

    // Este es el post seleccionado para usar en el BottomSheet
    var selectedPost by remember { mutableStateOf<Post?>(null) }

    // BottomShett
    if (isSheetOpen && selectedPost != null) {
        PostOptionsBottomSheet(
            post = selectedPost!!,
            onDismiss = { isSheetOpen = false },
            onReply = { post ->
                val id = post.id
                val author = post.author.encode()
                val message = post.message.encode()
                navController.navigate("addPost/$id/$author/$message")
            },
            onViewReplies = { post ->
                navController.navigate("quotes/${post.id}")
            },
            sheetState = sheetState,
        )
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
                Feed(
                    posts = postState.posts,
                    modifier = modifier.padding(paddingValues),
                    onOptionsClick = { post ->
                        selectedPost = post
                        isSheetOpen = true
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    },
                )
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
