package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignInRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.SignInResponse
import ar.edu.unlam.mobile.scaffolding.data.models.PostResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/v1/me/feed")
    suspend fun getFeed(): List<PostResponseDto>

    @POST("api/v1/users")
    suspend fun signIn(
        @Body signInRequest: SignInRequest,
    ): Response<SignInResponse>
}
