package com.example.topmovies.data.repository

import com.example.topmovies.data.database.dao.MovieDao
import com.example.topmovies.data.database.entities.MovieItemEntity
import com.example.topmovies.data.local.ImageCacheManager
import com.example.topmovies.data.model.MovieItem
import com.example.topmovies.data.model.MovieResponse
import com.example.topmovies.data.network.ImageService
import com.example.topmovies.data.network.MoviesService
import com.example.topmovies.domain.model.MovieData
import com.example.topmovies.domain.model.toDomain
import java.io.File
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiMovies: MoviesService,
    private val apiImages: ImageService,
    private val cacheManager: ImageCacheManager,
    private val movieDao: MovieDao
) {

    suspend fun getTopMoviesFromApi(): List<MovieData> {
        val response = apiMovies.getMovies()
        return response.results.map { it.toDomain() }
    }

    suspend fun getMovieDetail(movieId: Int): MovieData {
        val response = apiMovies.getMovieDetail(movieId)
        return response.toDomain()
    }

    suspend fun downloadAndSaveImage(imagePath: String): File? {
        val response = apiImages.getImage(imagePath)
        val inputStream = response.byteStream()
        return cacheManager.saveImage(imagePath.drop(0), inputStream)
    }

    fun getCachedImage(fileName: String): File {
        return cacheManager.getCachedImage(fileName)
    }

    fun isImageCached(fileName: String): Boolean {
        return cacheManager.isImageCached(fileName)
    }

    suspend fun getTopMoviesFromDatabase(): List<MovieData> {
        val response = movieDao.getAllMovies()
        return response.map { it.toDomain() }
    }

    suspend fun insertMovies(movies: List<MovieItemEntity>) {
        movieDao.insertAll(movies)
    }

    suspend fun clearMoviesDatabase() {
        movieDao.clearAllMovies()

    }

}