package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import android.content.Context
import androidx.core.content.edit

class AuthToken(
    context: Context,
) {
    // Almacenar token del usuario usando SharedPreferences
    // https://taourl.com/read-3043183.html?s=Store+login+access+token+into+sharedpreference+in+android+MVVM+architecture
    private val preferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    var userToken: String?
        get() =
            // "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImFubmFAZ21haWwuY29tIiwiZXh" +
            // "wIjoxNzUyMDkzMTc3LCJpc3MiOiJ1bmxhbS10dWl0ZXIiLCJuYW1lIjoiYW5uYUBnbWFpbC5j" +
            // "b20iLCJzdWIiOjE5OH0.lZxrpW1-T7roJxN7_vFg1NCeOrkn-W5XlaBeV1m8DfE"
            // Quitar el token hardcodeado y dejar esta linea cuando este la pantalla de login
            preferences.getString("Authorization", "")
        set(value) = preferences.edit { putString("Authorization", value) }

    val appToken: String = ar.edu.unlam.mobile.scaffolding.BuildConfig.API_KEY
}
