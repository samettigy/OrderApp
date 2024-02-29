package com.android.orderapp.data.service

import com.android.orderapp.data.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {

    @GET("movie/popular")
    suspend fun getLatestMovie(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<MovieListResponse>
}