package com.test.watched.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.watched.data.datamodels.Favorites

@Dao
interface FavoritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favorites: Favorites)

    @Query("SELECT * FROM favorite_movies_table")
    fun getAllFavoritesInfo(): LiveData<List<Favorites>>

    @Query("SELECT * FROM favorite_movies_table WHERE shortMovieInfoId = :movieId")
    suspend fun getFavoritedMovieById(movieId: Int) : Favorites?

    @Query("DELETE FROM favorite_movies_table")
    suspend fun deleteAllFavoritesInfo()

    @Query("DELETE FROM favorite_movies_table WHERE shortMovieInfoId = :movieId")
    suspend fun deleteFavoriteMovieWithId(movieId: Int)
}