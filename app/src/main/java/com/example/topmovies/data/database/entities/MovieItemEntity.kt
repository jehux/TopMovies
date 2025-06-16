package com.example.topmovies.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.topmovies.domain.model.MovieData


@Entity(tableName = "movie_table")
data class MovieItemEntity(
    @ColumnInfo(name = "adult")
    val isAdult: Boolean,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String,

    @ColumnInfo(name = "original_title")
    val originalTitle: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "poster_path")
    val posterPath: String?,

    @ColumnInfo(name = "release_date")
    val releaseDate: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "video")
    val hasVideo: Boolean,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int
)

fun MovieData.toDomain() = MovieItemEntity(
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