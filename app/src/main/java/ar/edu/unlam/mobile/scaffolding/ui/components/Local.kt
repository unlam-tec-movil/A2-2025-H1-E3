package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.runtime.staticCompositionLocalOf
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User

val LocalUser = staticCompositionLocalOf<User?> { null }

// https://developer.android.com/develop/ui/compose/compositionlocal?hl=es-419
// Define una fuente de datos global y reactiva accesible desde cualquier
// @Composable, sin necesidad de ViewModel o parámetros explícitos.
// LocalUser guarda el usuario actual (User?) en el árbol de composición.
// Se usa dentro de un CompositionLocalProvider.
// Ejemplo de uso
// val user = LocalUser.current
// Text("Hola ${user?.name}")

// Es util Porque en lugar de pasar el user como parámetro en todas las pantallas,
// lo inyectamos una vez en la raíz (ej: MainScreen):
// CompositionLocalProvider(LocalUser provides user) {
//    AppNavHost(...)
// }
// Desde ahí en adelante, cualquier parte de la UI puede acceder a LocalUser.current
// de forma directa y reactiva.
