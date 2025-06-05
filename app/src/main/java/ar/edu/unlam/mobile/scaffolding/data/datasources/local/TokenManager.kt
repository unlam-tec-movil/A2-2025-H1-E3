package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import android.content.Context
import javax.inject.Inject
import androidx.core.content.edit

class TokenManager
    @Inject
    constructor(
        context: Context,
    ) {
        private val preferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

        var userToken: String?
        get() = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InZpb2xlcm9hcmVzQGhvdG1haWwuY29tIiwiZXhwIjoxNzUxNTc0Njk1LCJpc3MiOiJ1bmxhbS10dWl0ZXIiLCJuYW1lIjoidmlvbGVyb2FyZXNAaG90bWFpbC5jb20iLCJzdWIiOjE4NX0.EZE1asen8XGSZFWl2d-FNmtF7TNfXb7U6DG1qX2-vDc"
            //preferences.getString("Authorization", "") // Quitar el token hardcodeado y dejar esta linea
        set(value) = preferences.edit() { putString("Authorization", value) }

        val appToken: String = ar.edu.unlam.mobile.scaffolding.BuildConfig.API_KEY
}