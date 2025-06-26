package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

@Composable
fun Favorites(
    users: List<User>,
    onBackClick: () -> Unit,
    modifier: Modifier,
    onFollowClick: (User) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomHeader("Usuarios Favoritos", onBack = onBackClick)
        },
    ) { padding ->
        LazyColumn(
            modifier =
                modifier
                    .padding()
                    .fillMaxSize()
                    .padding(padding),
        ) {
            if (users.isEmpty()) {
                item {
                    EmptyState(
                        message = "No hay usuarios favoritos aún.",
                        onBackClick = onBackClick,
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp),
                    )
                }
            } else {
                items(users) { user ->
                    FavoriteUserCard(
                        user,
                        onFollowClick = { onFollowClick(user) },
                    )
                }
            }
        }
    }
}
