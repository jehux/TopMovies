package com.example.topmovies.data.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface ImageApiService {
    @GET("/t/p/{quality}/{imagePath}")
    suspend fun getImage(
        @Path("imagePath") imagePath: String,
        @Path("quality") quality: String = "w300"
    ): ResponseBody
}