package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import ar.edu.unlam.mobile.scaffolding.ui.components.CustomHeader
import ar.edu.unlam.mobile.scaffolding.ui.components.Greeting

@Composable
fun UserScreen(
    userSessionViewModel: UserSessionViewModel = hiltViewModel(),
    navController: NavController,
) {
    val user by userSessionViewModel.user.collectAsState()

    Scaffold(
        topBar = {
            CustomHeader(title = "Mi perfil", onBack = { navController.popBackStack() })
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Greeting(user?.name ?: "")
        }
    }
}
