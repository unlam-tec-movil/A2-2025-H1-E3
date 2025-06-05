package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatFriendlyDate(isoDate: String?): String =
    try {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(isoDate, formatter)

        val outputFormatter =
            DateTimeFormatter.ofPattern(
                "d 'de' MMMM 'de' yyyy 'a las' HH:mm",
                Locale("es"),
            )
        dateTime.format(outputFormatter)
    } catch (e: Exception) {
        "Fecha inválida"
    }

@Composable
fun PostCard(
    post: Post,
    onLikeClick: () -> Unit,
    onReplyClick: () -> Unit,
) {
    val heartColor by animateColorAsState(
        targetValue = if (post.liked) Color.Red else Color.Gray,
        label = "heart color",
    )

    val likeCountText = remember(post.likes) { post.likes.toString() }

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
    ) {
        // Avatar
        Avatar(post.avatarUrl)

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Header: Author and date
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = post.author,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = formatFriendlyDate(post.date),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Message
            Text(
                text = post.message,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Actions
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onReplyClick) {
                    Icon(
                        imageVector = Icons.Outlined.ChatBubbleOutline,
                        contentDescription = "Reply",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }

                IconButton(onClick = onLikeClick) {
                    Icon(
                        imageVector = if (post.liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like",
                        tint = heartColor,
                    )
                }

                Text(
                    text = likeCountText,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
    // Línea separadora
    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
}
