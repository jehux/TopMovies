package com.example.topmovies.domain.model

import com.example.topmovies.data.database.entities.MovieItemEntity
import com.example.topmovies.data.model.MovieItem

data class MovieData(
    val isAdult: Boolean,
    val backdropPath: String?,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val releaseDate: String,
    val title: String,
    val hasVideo: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

fun MovieItem.toDomain() = MovieData(
    isAdult,
    backdropPath,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    hasVideo,
    voteAverage,
    voteCount
)

fun MovieItemEntity.toDomain() = MovieData(
    isAdult,
    backdropPath,
    id,
    originalLanguage,
    originalTitle,
    overview,
    popularity,
    posterPath,
    releaseDate,
    title,
    hasVideo,
    voteAverage,
    voteCount
)