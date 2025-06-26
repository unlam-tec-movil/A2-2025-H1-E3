package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Feed(
    posts: List<Post>,
    modifier: Modifier = Modifier,
    onOptionsClick: (Post) -> Unit,
    listState: LazyListState,
    header: @Composable () -> Unit,
    favoriteUsernames: Set<String>,
    onFollowClick: (Post) -> Unit,
    onLikeClick: (Post) -> Unit,
    currentUser: User?,
    contentPadding: PaddingValues,
) {
    val repliesMap: Map<Int, Int> = posts.groupingBy { it.parentId }.eachCount()
    LazyColumn(
        state = listState,
        modifier =
            Modifier
                .fillMaxSize(),
        contentPadding = contentPadding,
    ) {
        stickyHeader {
            header()
        }
        items(posts) { post ->
            val repliesCount = repliesMap[post.id] ?: 0
            val isFollowing = favoriteUsernames.contains(post.author)
            val isCurrentUserPost = currentUser?.name == post.author
            PostCard(
                post,
                repliesCount,
                onLikeClick = { onLikeClick(post) },
                onReplyClick = { onOptionsClick(post) },
                isFollowing = isFollowing,
                onFollowClick = { onFollowClick(post) },
                showFollowButton = !isCurrentUserPost,
            )
        }
    }
}
