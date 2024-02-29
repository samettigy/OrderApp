package com.android.orderapp.data.repository

import com.android.orderapp.data.model.MovieListResponse

interface MoviesRepository {
    suspend fun getLatestMovies(page: Int): Result<MovieListResponse>
}