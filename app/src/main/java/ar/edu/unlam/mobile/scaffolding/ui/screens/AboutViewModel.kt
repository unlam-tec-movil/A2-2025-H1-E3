package ar.edu.unlam.mobile.scaffolding.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AboutViewModel
    @Inject
    constructor() : ViewModel() {
        val teamMembers =
            listOf(
                "Chile Daiana",
                "Mucci Natalia",
                "Paez Federico",
                "Olivera Christian",
                "Gonzalo Nizzi",
                "Federico Carrazan",
            )

        val technologiesUsed =
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

        val courseInfo =
            listOf(
                "Programación Móvil 2",
                "Universidad Nacional de La Matanza",
                "Cátedra: De Pietro Maximiliano y Larreta Leonel",
            )
    }
