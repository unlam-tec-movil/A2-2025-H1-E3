package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    var logoVisible by remember { mutableStateOf(false) }
    var contentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        logoVisible = true
        contentVisible = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Acerca de...") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Volver")
                    }
                },
            )
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
                    painter = painterResource(id = R.drawable.logo_circle),
                    contentDescription = "Logo de la app",
                    modifier =
                        Modifier
                            .padding(top = 24.dp)
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
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "X Clone",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "Aplicación para Programación Móvil 2 de la Universidad de La Matanza.",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = "Cátedra de Maximiliano De Pietro y Leonel Larreta.",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()

                    Text(
                        text = "Tecnologías usadas:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                    )

//                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
//                        Text("• Clean Architecture")
//                        Text("• Jetpack Compose (UI moderna de Android)")
//                        Text("• Material 3 (Diseño visual moderno)")
//                        Text("• Navigation Compose (Navegación declarativa)")
//                        Text("• Retrofit (Llamadas HTTP)")
//                        Text("• Gson (Conversión JSON)")
//                        Text("• Room (Persistencia local)")
//                        Text("• Hilt (Inyección de dependencias)")
//                        Text("• Coil (Carga eficiente de imágenes)")
//                    }

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
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider()
                    Text(
                        text = "Desarrollado por:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
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
                            )
                        }
                    }

//                    Column(
//                        verticalArrangement = Arrangement.spacedBy(4.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                    ) {
//                        Text("Carrazan Federico")
//                        Text("Chile Daiana")
//                        Text("Irigoin Alan")
//                        Text("Mucci Natalia")
//                        Text("Nizzi Gonzalo")
//                        Text("Olivera Christian")
//                        Text("Paez Federico")
//                    }

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
                        modifier = Modifier.padding(top = 16.dp),
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
