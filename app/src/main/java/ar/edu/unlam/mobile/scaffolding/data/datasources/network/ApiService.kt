package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.LoginRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.request.SignInRequest
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.LoginResponse
import ar.edu.unlam.mobile.scaffolding.data.datasources.network.response.SignInResponse
import ar.edu.unlam.mobile.scaffolding.data.models.CreatePostRequestDto
import ar.edu.unlam.mobile.scaffolding.data.models.PostResponseDto
import ar.edu.unlam.mobile.scaffolding.data.models.ReplyRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/v1/me/feed")
    suspend fun getFeed(): List<PostResponseDto>

    @POST("api/v1/users")
    suspend fun signIn(
        @Body signInRequest: SignInRequest,
    ): Response<SignInResponse>

    @POST("api/v1/login")
    suspend fun login(
        @Body loginRequest: LoginRequest,
    ): Response<LoginResponse>

    @GET("/api/v1/me/tuits/{tuit_id}/replies")
    suspend fun getQuotes(
        @Path("tuit_id") postId: Int,
    ): List<PostResponseDto>

    @POST("/api/v1/me/tuits")
    suspend fun createPost(
        @Body request: CreatePostRequestDto,
    ): Response<Unit>

    @POST("/api/v1/me/tuits/{tuit_id}/replies")
    suspend fun createReply(
        @Path("tuit_id") postId: Int,
        @Body request: ReplyRequestDto,
    ): Response<Unit>
}
