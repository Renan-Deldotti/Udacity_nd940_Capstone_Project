package com.test.watched.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.watched.data.datamodels.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Query("SELECT * from movie_table WHERE id = :id")
    suspend fun getMovieById(id: Int): Movie

    @Query("UPDATE movie_table SET isFavorite=:isFavorited WHERE id = :movieId")
    suspend fun updateMovieFavoriteStatus(isFavorited: Boolean, movieId: Int)

    @Query("SELECT * from movie_table")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("DELETE FROM movie_table")
    suspend fun deleteAllMovies()
}