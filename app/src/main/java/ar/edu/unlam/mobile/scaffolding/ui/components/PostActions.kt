package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@Composable
fun PostActions(
    post: Post,
    repliesCount: Int,
    onLikeClick: () -> Unit,
    onReplyClick: () -> Unit,
    isFollowing: Boolean,
    onFollowClick: () -> Unit,
    showFollowButton: Boolean,
) {
    val heartColor by animateColorAsState(
        targetValue = if (post.liked) Color.Red else Color.Gray,
        label = "heart color",
    )
    val likeCountText = remember(post.likes) { post.likes.toString() }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onReplyClick) {
            Icon(
                imageVector = Icons.Outlined.ChatBubbleOutline,
                contentDescription = "Reply",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        if (repliesCount > 0) {
            Text(
                text = repliesCount.toString(),
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 8.dp),
            )
        }
        IconButton(onClick = onLikeClick) {
            Icon(
                imageVector = if (post.liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Like",
                tint = heartColor,
            )
        }
        if (post.likes > 0) {
            Text(
                text = likeCountText,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Spacer(modifier = Modifier.weight(1f)) // Esto empuja el botón hasta el final

        // Follow Button
        if (showFollowButton) {
            TextButton(onClick = onFollowClick) {
                Text(
                    text = if (isFollowing) "Siguiendo" else "Seguir",
                    style = MaterialTheme.typography.labelSmall,
                    color = if (isFollowing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}
