package com.example.topmovies.domain

import com.example.topmovies.data.database.entities.toDomain
import com.example.topmovies.data.repository.MoviesRepository
import com.example.topmovies.domain.model.MovieData
import javax.inject.Inject

class GetTopMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(): List<MovieData> {
        var movies = repository.getTopMoviesFromDatabase()
        if (movies.isEmpty()) {
            movies = repository.getTopMoviesFromApi()
            repository.insertMovies(movies.map { it.toDomain() })
        }
        return movies
    }

    suspend fun downloadImage(path: String) = repository.downloadAndSaveImage(path)

    fun isImageCached(fileName: String) = repository.isImageCached(fileName)

    fun getCachedImage(fileName: String) = repository.getCachedImage(fileName)

}