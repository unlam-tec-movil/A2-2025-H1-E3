package ar.edu.unlam.mobile.scaffolding.utils

import android.util.Patterns

object ValidationUtils {
    fun isEmailValid(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isPasswordValid(password: String): Boolean = password.length >= 3

    fun doPasswordsMatch(
        password: String,
        confirmPassword: String,
    ): Boolean = password == confirmPassword

    fun isNameValid(name: String): Boolean = name.length >= 3
}
