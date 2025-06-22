package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

@Composable
fun FavoriteUserCard(user: User) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
    ) {
        // Avatar
        Avatar(user.avatarUrl)

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Header: Author and date
            CustomHeader(
                title = user.name,
            ) { }
            Spacer(modifier = Modifier.height(4.dp))
            // Message
            Text(user.email)
        }
    }
}
