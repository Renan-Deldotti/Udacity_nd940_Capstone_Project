package com.test.watched.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.watched.data.datamodels.ShortMovieInfo

@Dao
interface ShortMovieInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg shortMovieInfo: ShortMovieInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShortInfo(shortMovieInfo: ShortMovieInfo)

    @Update
    suspend fun update(shortMovieInfo: ShortMovieInfo)

    @Query("UPDATE short_movie_info_table SET isFavorite=:isFavorited WHERE shortMovieId = :movieId")
    suspend fun updateFavoriteStatus(isFavorited: Boolean, movieId: Int)

    @Query("SELECT * from short_movie_info_table WHERE shortMovieId = :id")
    fun getShortInfoById(id: Int) : LiveData<ShortMovieInfo>

    @Query("SELECT * FROM short_movie_info_table WHERE shortMovieId IN (:ids)")
    fun getShortInfoByIdsAsList(vararg ids:Int): LiveData<List<ShortMovieInfo>>

    @Query("SELECT * from short_movie_info_table")
    fun getAllShortMovieInfo() : LiveData<List<ShortMovieInfo>>

    @Query("DELETE FROM short_movie_info_table")
    suspend fun deleteAllShortInfo()
}