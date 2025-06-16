package ar.edu.unlam.mobile.scaffolding.data.datasources.local

import android.content.Context
import androidx.core.content.edit
import ar.edu.unlam.mobile.scaffolding.domain.user.models.User
import com.google.gson.Gson

class AuthToken(
    context: Context,
) {
    // Almacenar token del usuario usando SharedPreferences
    // https://taourl.com/read-3043183.html?s=Store+login+access+token+into+sharedpreference+in+android+MVVM+architecture
    private val preferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    // Token del usuario (Authorization header)
    var userToken: String?
        get() =
            preferences.getString("Authorization", "")
        set(value) = preferences.edit { putString("Authorization", value) }

    // Usuario cacheado como JSON
    var cachedUser: User?
        get() {
            val json = preferences.getString("CachedUser", null)
            return json?.let { gson.fromJson(it, User::class.java) }
        }
        set(value) {
            val json = gson.toJson(value)
            preferences.edit { putString("CachedUser", json) }
        }

    val appToken: String = ar.edu.unlam.mobile.scaffolding.BuildConfig.API_KEY
}
