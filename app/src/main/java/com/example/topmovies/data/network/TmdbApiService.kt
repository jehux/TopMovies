package com.example.topmovies.data.network

import com.example.topmovies.data.model.MovieItem
import com.example.topmovies.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Header("Authorization") apiKey: String,
        @Header("accept") accept: String = "application/json",
        @Query("language") language: String = "es",
        @Query("page") page: Int = 1
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int = 1,
        @Header("Authorization") apiKey: String,
        @Header("accept") accept: String = "application/json",
        @Query("language") language: String = "es"
    ): MovieItem
}