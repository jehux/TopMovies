package com.example.topmovies.data.network

import com.example.topmovies.data.model.MovieItem
import com.example.topmovies.data.model.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MoviesService @Inject constructor(
    private val api: TmdbApiService
){

    private val apiKey = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkOTRkZGVjNDdhMzMzYjllNDI1MGNjNzY3YTQ3NzVkYyIsIm5iZiI6MTc1MDAyNTY3NS42NTkwMDAyLCJzdWIiOiI2ODRmNDVjYmVjNzMyOTNjZDdiYjUzZjMiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.6IuKqJtwXDyXI0qOOxazZFN3FPFfAOT701cA0EzxodM"

    suspend fun getMovies(): MovieResponse {
        return withContext(Dispatchers.IO) {
            val response = api.getTopRatedMovies(apiKey)
            response
        }
    }

    suspend fun getMovieDetail(movieId: Int): MovieItem {
        return withContext(Dispatchers.IO) {
            val response = api.getMovieDetail(movieId, apiKey)
            response
        }
    }

}