package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostOptionsSheetHandler(
    isOpen: Boolean,
    post: Post?,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onReply: (Post) -> Unit,
    onViewReplies: (Post) -> Unit,
) {
    if (isOpen && post != null) {
        PostOptionsBottomSheet(
            post = post,
            onDismiss = onDismiss,
            onReply = onReply,
            onViewReplies = onViewReplies,
            sheetState = sheetState,
        )
    }
}
