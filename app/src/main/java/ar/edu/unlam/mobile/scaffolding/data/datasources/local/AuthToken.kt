package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import javax.inject.Inject

class AuthToken
    @Inject
    constructor(
        context: Context,
    ) {
        // Almacenar token del usuario usando SharedPreferences
        // https://taourl.com/read-3043183.html?s=Store+login+access+token+into+sharedpreference+in+android+MVVM+architecture
        private val preferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

        var userToken: String?
            get() {
                /*"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InZpb2xlcm9hcmVzQGhvd" +
                    "G1haWwuY29tIiwiZXhwIjoxNzUxNTc0Njk1LCJpc3MiOiJ1bmxhbS10dWl0ZXIiLCJu" +
                    "YW1lIjoidmlvbGVyb2FyZXNAaG90bWFpbC5jb20iLCJzdWIiOjE4NX0.EZE1asen8XGSZFW" +
                    "l2d-FNmtF7TNfXb7U6DG1qX2-vDc"
                Quitar el token hardcodeado y dejar esta linea cuando este la pantalla de login
                 preferences.getString("Authorization", "")*/
                val token = preferences.getString("Authorization", null)
                Log.d("TokenManager", "Token obtenido: $token")
                return token
            }
            set(value) = preferences.edit { putString("Authorization", value) }

        //  val appToken: String = ar.edu.unlam.mobile.scaffolding.BuildConfig.API_KEY
        val appToken: String = "8f2cf9e515633e77df4f558c39afb4e7cd196890d800820d61bb56fef926be51"
    }
