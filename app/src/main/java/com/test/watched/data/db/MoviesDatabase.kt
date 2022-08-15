package com.test.watched.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.watched.data.datamodels.ShortMovieInfo

@Database(entities = [ShortMovieInfo::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val shortMovieInfoDao: ShortMovieInfoDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java,
                        "movies_info_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}