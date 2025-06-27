package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomHeader

@Composable
fun AboutScreen(navController: NavController) {
    val isDarkTheme = isSystemInDarkTheme()
    var logoVisible by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        logoVisible = true
        contentVisible = true
    }

    Scaffold(
        topBar = {
            CustomHeader(title = "Acerca de...", onBack = { navController.popBackStack() })
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Logo animado
            AnimatedVisibility(
                visible = logoVisible,
                enter =
                    fadeIn(animationSpec = tween(1000)) +
                        scaleIn(initialScale = 0.8f, animationSpec = tween(1000)),
            ) {
                Image(
                    painterResource(
                        id = if (isDarkTheme) R.drawable.logo_light else R.drawable.logo_dark,
                    ),
                    contentDescription = "Logo de la app",
                    modifier =
                        Modifier
                            .padding(top = 15.dp)
                            .size(100.dp)
                            .clip(CircleShape),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contenido con scroll dentro de la animación
            AnimatedVisibility(
                visible = contentVisible,
                enter = fadeIn(animationSpec = tween(700)),
            ) {
                Column(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                            .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Text(
                        text = "X Clone",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "Aplicación para Programación Móvil 2, Universidad de La Matanza.",
                        style =
                            MaterialTheme.typography.bodyLarge.copy(
                                lineHeight = 22.sp,
                            ),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = "Cátedra: \n Maximiliano De Pietro y Leonel Larreta.",
                        style =
                            MaterialTheme.typography.bodyLarge.copy(
                                lineHeight = 22.sp,
                            ),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier)

                    HorizontalDivider()

                    Column(modifier = Modifier.padding(6.dp)) {
                        Row(modifier = Modifier.padding(bottom = 10.dp)) {
                            Column(Modifier.padding(end = 14.dp)) {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(30.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surfaceVariant),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Configuración",
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "Tecnologías usadas:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )

                                val tecnologias =
                                    listOf(
                                        "Clean Architecture",
                                        "Jetpack Compose (UI moderna de Android)",
                                        "Material 3 (Diseño visual moderno)",
                                        "Navigation Compose (Navegación declarativa)",
                                        "Retrofit (Llamadas HTTP)",
                                        "Gson (Conversión JSON)",
                                        "Room (Persistencia local)",
                                        "Hilt (Inyección de dependencias)",
                                        "Coil (Carga eficiente de imágenes)",
                                    )

                                Column(
                                    modifier =
                                        Modifier
                                            .fillMaxSize(),
                                ) {
                                    tecnologias.forEach { tecnologia ->
                                        Text(
                                            text = "• $tecnologia",
                                            fontSize = 14.sp,
                                            lineHeight = 22.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                        )
                                    }
                                }
                            }
                        }

                        Row(modifier = Modifier.padding(top = 10.dp)) {
                            Column(Modifier.padding(end = 14.dp)) {
                                Box(
                                    modifier =
                                        Modifier
                                            .size(30.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surfaceVariant),
                                    contentAlignment = Alignment.Center,
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Group,
                                        contentDescription = "Configuración",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(20.dp),
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "Desarrollado por:",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onBackground,
                                )

                                val alumnos =
                                    listOf(
                                        "Carrazan Federico",
                                        "Chile Daiana",
                                        "Irigoin Alan",
                                        "Mucci Natalia",
                                        "Nizzi Gonzalo",
                                        "Olivera Christian",
                                        "Paez Federico",
                                    )

                                Column(
                                    modifier =
                                        Modifier
                                            .fillMaxSize(),
                                ) {
                                    alumnos.forEach { alumno ->
                                        Text(
                                            text = "• $alumno",
                                            fontSize = 14.sp,
                                            lineHeight = 22.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                        )
                                    }
                                }
                            }
                        }

                        val context = LocalContext.current

                        Button(
                            onClick = {
                                val intent =
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://github.com/unlam-tec-movil/A2-2025-H1-E3"),
                                    )
                                context.startActivity(intent)
                            },
                            modifier =
                                Modifier
                                    .padding(top = 16.dp)
                                    .align(Alignment.CenterHorizontally),
                        ) {
                            Icon(Icons.Default.OpenInBrowser, contentDescription = "GitHub")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ver en GitHub")
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}
