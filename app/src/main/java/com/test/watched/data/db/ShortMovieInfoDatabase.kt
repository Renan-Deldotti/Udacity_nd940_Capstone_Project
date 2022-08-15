package com.test.watched.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.watched.data.datamodels.ShortMovieInfo

@Database(entities = [ShortMovieInfo::class], version = 1, exportSchema = false)
abstract class ShortMovieInfoDatabase : RoomDatabase() {

    abstract val shortMovieInfoDao: ShortMovieInfoDao

    companion object {
        @Volatile
        private var INSTANCE: ShortMovieInfoDatabase? = null

        fun getInstance(context: Context): ShortMovieInfoDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShortMovieInfoDatabase::class.java,
                        "short_movie_info_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}