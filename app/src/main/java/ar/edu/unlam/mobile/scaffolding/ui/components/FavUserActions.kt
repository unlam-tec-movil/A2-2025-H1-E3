package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

@Composable
fun FavUserActions(
    user: User,
    onUnfavClick: () -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onUnfavClick) {
            Icon(
                imageVector = Icons.Filled.HeartBroken,
                contentDescription = "Unfav"
            )
        }
    }
}