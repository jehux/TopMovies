package com.example.topmovies.domain

import com.example.topmovies.data.repository.MoviesRepository
import com.example.topmovies.domain.model.MovieData
import java.io.File
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MoviesRepository
) {
    suspend fun getMovieDetail(id: Int): MovieData {
        val movie = repository.getMovieDetail(id)
        return movie
    }

    fun getImageFromDevice(fileName: String): File {
        return repository.getCachedImage(fileName)
    }
}
