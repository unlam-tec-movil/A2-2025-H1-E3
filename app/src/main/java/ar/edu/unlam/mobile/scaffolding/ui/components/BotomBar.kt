package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(controller: NavHostController) {
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    NavigationBar(
        modifier =
            Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
    ) {
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
