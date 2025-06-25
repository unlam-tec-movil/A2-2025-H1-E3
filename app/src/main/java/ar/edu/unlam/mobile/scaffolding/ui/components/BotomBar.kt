package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    NavigationBar {
        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "home" } == true,
            onClick = { controller.navigate("home") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )

        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "draft" } == true,
            onClick = { controller.navigate("draft") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Drafts,
                    contentDescription = "Borradores",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )

        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "favorites" } == true,
            onClick = { controller.navigate("favorites") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favoritos",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )

//        NavigationBarItem(
//            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "user" } == true,
//            onClick = { controller.navigate("user") },
//            icon = {
//                Icon(
//                    imageVector = Icons.Default.AccountBox,
//                    contentDescription = "User",
//                    tint = MaterialTheme.colorScheme.primary,
//                )
//            },
//        )

        NavigationBarItem(
            selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == "about" } == true,
            onClick = { controller.navigate("about") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Acerca de",
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
        )
    }
}
