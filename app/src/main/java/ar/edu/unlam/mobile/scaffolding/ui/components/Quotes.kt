package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@Composable
fun Quotes(
    posts: List<Post>,
    modifier: Modifier,
    onBackClick: () -> Unit,
) {
    if (posts.isEmpty()) {
        // Mostrar mensaje vacío con botón volver
        EmptyState(
            message = "No hay items para mostrar",
            onBackClick = onBackClick,
            modifier = modifier,
        )
    } else {
        Scaffold(
            topBar = {
                CustomHeader("Respuestas", onBack = onBackClick)
            },
        ) { padding ->
            LazyColumn(
                modifier =
                    modifier
                        .fillMaxSize()
                        .padding(padding),
            ) {
                items(posts) { post ->
                    QuoteCard(post)
                }
            }
        }
    }
}
