package ar.edu.unlam.mobile.scaffolding.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.R
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomHeader

@Composable
fun AboutScreen(
    navController: NavController,
    viewModel: AboutViewModel = hiltViewModel(),
) {
    val team = viewModel.teamMembers
    val tech = viewModel.technologiesUsed
    val course = viewModel.courseInfo
    val context = LocalContext.current
    var logoVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        logoVisible = true
    }

    Scaffold(
        topBar = {
            CustomHeader(title = "Acerca de", onBack = { navController.popBackStack() })
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Logo

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

            Text(
                text = "X Clone",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card con toda la información
            Card(
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                ) {
                    SectionTitle("🏫 Información académica")

                    RowInfo(icon = Icons.Default.School, text = course[0])
                    RowInfo(icon = Icons.Default.LocationCity, text = course[1])
                    RowInfo(icon = Icons.Default.Person, text = course[2])

                    SectionTitle("👥 Integrantes del proyecto")
                    team.forEach { Text("• $it", style = MaterialTheme.typography.bodyMedium) }

                    Spacer(modifier = Modifier.height(16.dp))

                    SectionTitle("🛠 Tecnologías utilizadas")
                    tech.forEach { Text("• $it", style = MaterialTheme.typography.bodyMedium) }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

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

@Composable
fun RowInfo(
    icon: ImageVector,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier =
                Modifier
                    .size(20.dp)
                    .padding(end = 8.dp),
        )
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary),
        modifier = Modifier.padding(bottom = 8.dp),
    )
}
