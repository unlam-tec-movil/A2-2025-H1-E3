package ar.edu.unlam.mobile.scaffolding.utils

import android.util.Patterns
import java.util.Locale

object ValidationUtils {
    fun isEmailValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String): Boolean = password.length >= 3

    fun doPasswordsMatch(
        password: String,
        confirmPassword: String,
    ): Boolean = password == confirmPassword

    fun isNameValid(name: String): Boolean = name.length >= 3
}

fun formatLikes(count: Long): String =
    when {
        count < 1_000 -> "$count"
        count < 1_000_000 -> String.format(Locale.US, "%.1f", count / 1_000.0).removeSuffix(".0") + "K"
        count < 1_000_000_000 -> String.format(Locale.US, "%.1f", count / 1_000_000.0).removeSuffix(".0") + "M"
        count < 1_000_000_000_000 -> String.format(Locale.US, "%.1f", count / 1_000_000_000.0).removeSuffix(".0") + "B"
        else -> "999B+"
    }
