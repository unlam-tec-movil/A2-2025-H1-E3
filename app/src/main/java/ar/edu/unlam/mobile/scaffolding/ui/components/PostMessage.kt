package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@Composable
fun PostMessage(post: Post) {
    Text(
        text = post.message,
        fontSize = 14.sp,
        lineHeight = 22.sp,
        color = MaterialTheme.colorScheme.onBackground,
    )
}
