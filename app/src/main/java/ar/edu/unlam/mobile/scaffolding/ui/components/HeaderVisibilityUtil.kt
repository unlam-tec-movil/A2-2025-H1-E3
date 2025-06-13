package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun rememberHeaderVisibility(listState: LazyListState): State<Boolean> {
    // Variables para trackear scroll previo y detectar dirección
    var prevIndex by remember { mutableStateOf(0) }
    var prevOffset by remember { mutableStateOf(0) }

    return remember {
        derivedStateOf {
            val index = listState.firstVisibleItemIndex
            val offset = listState.firstVisibleItemScrollOffset

            val scrollingUp =
                when {
                    index < prevIndex -> true
                    index > prevIndex -> false
                    else -> offset < prevOffset
                }

            prevIndex = index
            prevOffset = offset
            // Devuelve true si estamos arriba de ttodo de la lista
            // o si la lista se esta moviendo hacia abajo (dedo de arriba hacia ABAJO)
            index == 0 || scrollingUp
        }
    }
}
