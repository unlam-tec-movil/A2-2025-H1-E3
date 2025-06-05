package ar.edu.unlam.mobile.scaffolding.data.datasources.network

import ar.edu.unlam.mobile.scaffolding.domain.post.models.Post
import retrofit2.http.GET

interface ApiService {
    @GET("api/v1/me/feed")
    suspend fun getFeed(): List<Post>
}
