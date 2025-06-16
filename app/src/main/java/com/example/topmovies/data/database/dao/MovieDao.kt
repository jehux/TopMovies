package com.example.topmovies.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.topmovies.data.database.entities.MovieItemEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY vote_average DESC")
    suspend fun getAllMovies(): List<MovieItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieItemEntity>)

    @Query("DELETE FROM movie_table")
    suspend fun clearAllMovies()
}