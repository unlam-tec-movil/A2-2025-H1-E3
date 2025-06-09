package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.utils.decodeBase64ToBitmap
import coil.compose.AsyncImage

@Composable
fun Avatar(
    avatarUrl: String?,
    modifier: Modifier = Modifier,
    defaultImageRes: Int = R.drawable.ic_launcher_foreground,
) {
    val model =
        remember(avatarUrl) {
            when {
                avatarUrl.isNullOrBlank() -> null
                avatarUrl.startsWith("data:image") -> decodeBase64ToBitmap(avatarUrl)
                else -> "$avatarUrl&size=256&bold=true&background=random&color=ffffff"
            }
        }

    if (model != null) {
        AsyncImage(
            model = model,
            contentDescription = "avatar",
            modifier =
                modifier
                    .size(48.dp)
                    .border(1.5.dp, MaterialTheme.colorScheme.surfaceBright, CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    } else {
        Image(
            painter = painterResource(id = defaultImageRes),
            contentDescription = "default avatar",
            modifier =
                modifier
                    .size(48.dp)
                    .border(1.5.dp, MaterialTheme.colorScheme.surfaceBright, CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape)
                    .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
    }
}
