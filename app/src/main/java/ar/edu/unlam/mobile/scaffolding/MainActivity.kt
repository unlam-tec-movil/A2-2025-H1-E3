package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.unlam.mobile.scaffolding.ui.components.BottomBar
import ar.edu.unlam.mobile.scaffolding.ui.screens.AboutScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.FavUsersScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.HomeScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.LoginScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.QuotesScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.SignInScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.SplashViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.UserScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.post.CreatePostScreen
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldingV2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: SplashViewModel = hiltViewModel()) {
    // Controller es el elemento que nos permite navegar entre pantallas. Tiene las acciones
    // para navegar como naviegate y también la información de en dónde se "encuentra" el usuario
    // a través del back stack
    val startDestination by viewModel.startDestination.collectAsState()
    val controller = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val navBackStackEntry = controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    // agregamos las rutas donde queremos que no se vea la bottom bar o el fav
    val hideBottomBarRoutes = listOf("addPost", "login", "signIn", "quotes", "user", "about")

    val shouldHideBottomBarAndFav =
        hideBottomBarRoutes.any { prefix -> currentRoute?.startsWith(prefix) == true }

    Scaffold(
        // oculta la nav bar en pantalla de registro
        bottomBar = {
            if (!shouldHideBottomBarAndFav) {
                BottomBar(controller = controller)
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
                    onClick = { controller.navigate("addPost") },
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Crear Post")
                }
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ) { paddingValue ->
        // NavHost es el componente que funciona como contenedor de los otros componentes que
        // podrán ser destinos de navegación.
        NavHost(navController = controller, startDestination = startDestination) {
            // composable es el componente que se usa para definir un destino de navegación.
            // Por parámetro recibe la ruta que se utilizará para navegar a dicho destino.
            composable("home") {
                // Home es el componente en sí que es el destino de navegación.
                HomeScreen(
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValue),
                    navController = controller,
                )
            }
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        controller.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = { controller.navigate("signIn") },
                )
            }
            composable("signIn") {
                SignInScreen(
                    onSignInSuccess = {
                        controller.navigate("home") {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    onNavigateToLogin = { controller.navigate("login") },
                )
            }
            composable(
                route = "user",
            ) {
                UserScreen(navController = controller)
            }
            composable(
                route = "quotes/{postId}",
                arguments =
                    listOf(
                        navArgument("postId") { type = NavType.IntType },
                    ),
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: return@composable
                QuotesScreen(
                    navController = controller,
                    snackbarHostState = snackbarHostState,
                    modifier = Modifier.padding(paddingValue),
                    postId = postId,
                )
            }

            composable(
                route = "addPost?replyTo={replyTo}",
                arguments =
                    listOf(
                        navArgument("replyTo") {
                            type = NavType.IntType
                            defaultValue = -1
                        },
                    ),
            ) { backStackEntry ->
                val replyTo = backStackEntry.arguments?.getInt("replyTo")?.takeIf { it != -1 }
                CreatePostScreen(navController = controller, parentId = replyTo)
            }

            composable("favorites") {
                FavUsersScreen(
                    snackbarHostState = snackbarHostState,
                    navController = controller,
                    modifier = Modifier.padding(paddingValue),
                )
            }

            composable("about") {
                AboutScreen(navController = controller)
            }
        }
    }
}
