package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

            Text(
                text = user.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = user.email,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                fontSize = 12.sp,
            )
        }
    }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
}
