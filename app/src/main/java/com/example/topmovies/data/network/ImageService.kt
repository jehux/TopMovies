package com.example.topmovies.data.network

import com.example.topmovies.data.model.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject

class ImageService @Inject constructor(
    private val api: ImageApiService
) {
    suspend fun getImage(imagePath: String): ResponseBody {
        return withContext(Dispatchers.IO) {
            val response = api.getImage(imagePath)
            response
        }
    }
}