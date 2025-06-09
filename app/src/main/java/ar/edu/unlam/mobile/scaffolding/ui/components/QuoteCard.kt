package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@Composable
fun QuoteCard(post: Post) {
    Row(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        // Avatar
        Avatar(post.avatarUrl)

        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            // Header: Author and date
            PostHeader(post)
            Spacer(modifier = Modifier.height(4.dp))
            // Message
            PostMessage(post)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    // Línea separadora
    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
}
