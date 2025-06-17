package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Feed(
    posts: List<Post>,
    repliesMap: Map<Int, Int>,
    modifier: Modifier,
    onOptionsClick: (Post) -> Unit,
    listState: LazyListState,
    header: @Composable () -> Unit,
    favoriteUsernames: Set<String>,
    onFollowClick: (Post) -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier =
            modifier
                .fillMaxSize()
                .padding(
                    bottom =
                        WindowInsets.navigationBars
                            .asPaddingValues()
                            .calculateBottomPadding(),
                ),
    ) {
        stickyHeader {
            header()
        }
        items(posts) { post ->
            val repliesCount = repliesMap[post.id] ?: 0
            val isFollowing = favoriteUsernames.contains(post.author)
            PostCard(
                post,
                repliesCount,
                onLikeClick = {},
                onReplyClick = { onOptionsClick(post) },
                isFollowing = isFollowing,
                onFollowClick = { onFollowClick(post) },
            )
        }
    }
}
