package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@Composable
fun PostActions(
    post: Post,
    onLikeClick: () -> Unit,
    onReplyClick: () -> Unit,
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
