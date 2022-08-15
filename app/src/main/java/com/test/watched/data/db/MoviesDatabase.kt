package com.test.watched.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.test.watched.data.datamodels.Converters
import com.test.watched.data.datamodels.Favorites
import com.test.watched.data.datamodels.Movie
import com.test.watched.data.datamodels.ShortMovieInfo

@Database(entities = [ShortMovieInfo::class, Movie::class, Favorites::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val shortMovieInfoDao: ShortMovieInfoDao
    abstract val movieDao: MovieDao
    abstract val favoritesDao: FavoritesDao

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