package com.example.topmovies.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topmovies.domain.GetMovieDetailUseCase
import com.example.topmovies.domain.model.MovieData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private var getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _movie = MutableStateFlow<MovieData?>(null)
    val movieModel: StateFlow<MovieData?> = _movie

    private val _posterImage = MutableStateFlow<File?>(null)
    val posterImage: StateFlow<File?> = _posterImage

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _isLoading = MutableStateFlow<Boolean?>(false)
    val isLoading = _isLoading

    fun fetchMovie(id: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val response = getMovieDetailUseCase.getMovieDetail(id)
                response.posterPath?.let { imageName ->
                    val image = getMovieDetailUseCase.getImageFromDevice(imageName.drop(0))
                    _posterImage.value = image
                }
                _movie.value = response
                _isLoading.value = false
            } catch (e: Exception) {
                _error.value = e.localizedMessage
                _isLoading.value = false
            }
        }
    }

}