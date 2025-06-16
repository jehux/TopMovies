package com.example.topmovies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.topmovies.data.database.dao.MovieDao
import com.example.topmovies.data.database.entities.MovieItemEntity

@Database(entities = [MovieItemEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}