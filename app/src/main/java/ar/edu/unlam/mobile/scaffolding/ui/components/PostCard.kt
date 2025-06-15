package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@Composable
fun PostCard(
    post: Post,
    repliesCount: Int,
    onLikeClick: () -> Unit,
    onReplyClick: () -> Unit,
) {
    val heartColor by animateColorAsState(
        targetValue = if (post.liked) Color.Red else Color.Gray,
        label = "heart color",
    )

    val likeCountText = remember(post.likes) { post.likes.toString() }

    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        // Avatar
        Avatar(post.avatarUrl)

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            // Header: Author and date
            PostHeader(post)
            Spacer(modifier = Modifier.height(4.dp))
            // Message
            PostMessage(post)
            // Actions
            PostActions(
                post = post,
                repliesCount = repliesCount,
                onReplyClick = onReplyClick,
                onLikeClick = onLikeClick,
            )
        }
    }
    // Línea separadora
    HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
}
