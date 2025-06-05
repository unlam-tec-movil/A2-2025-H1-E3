package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.LoginResponse
import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/v1/me/feed")
    suspend fun getFeed(): List<Post>

    @POST("/api/v1/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponse>
}
