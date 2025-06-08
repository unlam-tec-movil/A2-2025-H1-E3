package ar.edu.unlam.mobile.scaffolding.data.datasources.network.request

data class SignInRequest(
    val name: String,
    val email: String,
    val password: String,
)
