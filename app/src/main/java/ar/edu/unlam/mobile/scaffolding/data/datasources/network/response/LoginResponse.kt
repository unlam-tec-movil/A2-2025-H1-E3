package ar.edu.unlam.mobile.scaffolding.data.datasources.network.response

data class LoginResponse(
    val email: String,
    val password: String,
    val token: String,
)
