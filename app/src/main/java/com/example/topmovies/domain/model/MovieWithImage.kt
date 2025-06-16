package com.example.topmovies.domain.model

import java.io.File

data class MovieWithImage(
    val movie: MovieData,
    val imageFile: File?
)
