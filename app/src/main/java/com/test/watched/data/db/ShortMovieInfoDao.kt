package com.test.watched.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.test.watched.data.datamodels.ShortMovieInfo

@Dao
interface ShortMovieInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg shortMovieInfo: ShortMovieInfo)

    @Update
    suspend fun update(shortMovieInfo: ShortMovieInfo)

    @Query("SELECT * from short_movie_info_table WHERE movieId = :id")
    fun getShortInfoById(id: Int) : LiveData<ShortMovieInfo>

    @Query("SELECT * from short_movie_info_table")
    fun getAllShortMovieInfo() : LiveData<List<ShortMovieInfo>>

    @Query("DELETE FROM short_movie_info_table")
    suspend fun deleteAllShortInfo()
}