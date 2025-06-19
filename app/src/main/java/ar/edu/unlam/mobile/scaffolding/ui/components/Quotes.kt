package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@Composable
fun Quotes(
    posts: List<Post>,
    modifier: Modifier,
    onBackClick: () -> Unit,
    quotedPost: Post,
) {
    Scaffold(
        topBar = {
            CustomHeader("Respuestas de:", onBack = onBackClick)
        },
    ) { padding ->
        LazyColumn(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(padding),
        ) {
            item {
                QuoteCard(quotedPost)
            }
            if (posts.isEmpty()) {
                item {
                    EmptyState(
                        message = "No hay respuestas para este post aún.",
                        onBackClick = onBackClick,
                        modifier = Modifier.fillMaxSize().padding(top = 32.dp),
                    )
                }
            } else {
                items(posts) { post ->
                    QuoteCard(post)
                }
            }
        }
    }
}
