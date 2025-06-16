package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

@Composable
fun FavUserCard(
    user: User,
    onUnfavClick: () -> Unit,
){
    Row(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
        Avatar(user.avatarUrl)
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)){
            UserHeader(user)
            Spacer(modifier = Modifier.height(4.dp))
            FavUserActions(
                user = user,
                onUnfavClick = onUnfavClick,
            )
        }
    }
}