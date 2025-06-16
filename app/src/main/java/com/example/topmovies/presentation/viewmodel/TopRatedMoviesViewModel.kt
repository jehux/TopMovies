package com.example.topmovies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmovies.data.model.MovieItem
import com.example.topmovies.domain.GetTopMoviesUseCase
import com.example.topmovies.domain.model.MovieData
import com.example.topmovies.domain.model.MovieWithImage
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private var getTopMoviesUseCase: GetTopMoviesUseCase
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _movies = MutableStateFlow<List<MovieData>>(emptyList())
    val moviesModel: StateFlow<List<MovieData>> = _movies

    private val _imageFiles = MutableStateFlow<List<File?>>(emptyList())
    val imageFiles: StateFlow<List<File?>> = _imageFiles

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow<Boolean?>(false)
    val isLoading = _isLoading

    fun fetchTopRatedMovies() {
        viewModelScope.launch {
            try {
                val response = getTopMoviesUseCase.invoke()

                val imageDownloadJobs = response.map { movie ->
                    async(Dispatchers.IO) {
                        val imageName = movie.posterPath?.removePrefix("/") ?: return@async
                        if (!getTopMoviesUseCase.isImageCached(imageName)) {
                            getTopMoviesUseCase.downloadImage(imageName)
                        }
                    }
                }
                imageDownloadJobs.awaitAll()

                val images: MutableList<File?> = mutableListOf()
                response.forEach { movie ->
                    val imageName = movie.posterPath?.removePrefix("/") ?: ""
                    images.add(getTopMoviesUseCase.getCachedImage(imageName))
                }

                _movies.value = response
                _imageFiles.value = images
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.localizedMessage
                _isLoading.value = false
            }
        }
    }

    fun logAOut() {
        auth.signOut()
    }
}
