package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.components.Feed
import ar.edu.unlam.mobile.scaffolding.ui.components.LocalUser
import ar.edu.unlam.mobile.scaffolding.ui.components.LogoutConfirmationDialog
import ar.edu.unlam.mobile.scaffolding.ui.components.PostOptionsBottomSheet
import ar.edu.unlam.mobile.scaffolding.ui.components.UserHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.rememberHeaderVisibility
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
) {
    val userSessionViewModel: UserSessionViewModel = hiltViewModel()
    val user = LocalUser.current
    val uiState: PostUIState by viewModel.uiState.collectAsState()
    val favoriteUsers by viewModel.favoriteUsers.collectAsState()

    // Actualizar la lista de post luego de crear uno nuevo
    // 1. Sacamos un StateFlow<Boolean> con un valor por defecto
    val shouldRefreshFlow =
        navController.currentBackStackEntry
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

    // Actualizar displayedUser solo si user no es null
    var displayedUser by remember { mutableStateOf(user) }
    LaunchedEffect(user) {
        if (user != null) {
            displayedUser = user
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
                navController.navigate("addPost?replyTo=${post.id}")
            },
            onViewReplies = { post ->
                navController.navigate("quotes/${post.id}")
            },
            sheetState = sheetState,
        )
    }

    // State y lógica para el scroll y visibilidad del header de la LazyColumn
    val listState = rememberLazyListState()
    val showHeader by rememberHeaderVisibility(listState)

    // Estado para mostrar diálogo de confirmación logout
    var showLogoutDialog by remember { mutableStateOf(false) }

    // Diálogo de confirmación de Logout
    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
                userSessionViewModel.logout()
            },
            onDismiss = { showLogoutDialog = false },
            showDialog = showLogoutDialog,
        )
    }

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    // agregamos las rutas donde queremos que no se vea la bottom bar o el fav
    val hideBottomBarRoutes = listOf("addPost", "login", "signIn", "quotes", "user", "about")

    val shouldHideBottomBarAndFav =
        hideBottomBarRoutes.any { prefix -> currentRoute?.startsWith(prefix) == true }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // La bottom bar y el fav lo movimos a home
        bottomBar = {
            if (!shouldHideBottomBarAndFav) {
                BottomBar(controller = navController)
            }
        },
        floatingActionButton = {
            // oculta el botón en algunas pantallas
            if (!shouldHideBottomBarAndFav) {
                IconButton(
                    modifier =
                        Modifier.background(
                            MaterialTheme.colorScheme.primaryContainer,
                            CircleShape,
                        ),
                    onClick = { navController.navigate("addPost") },
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Crear Post")
                }
            }
        },
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
                    listState = listState,
                    header = {
                        AnimatedVisibility(visible = showHeader) {
                            UserHeader(
                                user = displayedUser!!,
                                onProfileClick = { navController.navigate("user") },
                                onLogoutClick = { showLogoutDialog = true },
                            )
                        }
                    },
                    favoriteUsernames = favoriteUsers.map { it.name }.toSet(),
                    onFollowClick = { post -> viewModel.toggleFavorite(post) },
                    onLikeClick = { post -> viewModel.toggleLike(post) },
                    currentUser = displayedUser,
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
