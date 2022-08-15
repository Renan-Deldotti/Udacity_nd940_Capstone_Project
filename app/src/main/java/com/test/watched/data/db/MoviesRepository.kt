package com.test.watched.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.test.watched.data.datamodels.Favorites
import com.test.watched.data.datamodels.Movie
import com.test.watched.data.datamodels.ShortMovieInfo
import com.test.watched.data.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MoviesRepository(private val database: MoviesDatabase) {

    private var lastMovieId: Int = -1

    suspend fun fetchMoviesFromApi(page: Int, filterResults: Boolean) {
        if (page > 1000) {
            Log.d(TAG, "fetchItemsFromAPI: max items loaded, returning")
            return
        }

        withContext(Dispatchers.IO) {
            val moviesResult = RetrofitInstance.api.getPopularMovies(page = page.toString())
            val unfilteredMoviesList = moviesResult.results

            if (unfilteredMoviesList.isNotEmpty()) {
                val resultList = if (filterResults) {
                    val filteredList = unfilteredMoviesList.filter {
                        it.adult == false
                    }
                    filteredList
                } else {
                    unfilteredMoviesList
                }
                database.shortMovieInfoDao.insertAll(*resultList.toTypedArray())
            }
        }
    }

    val allShortMovieInfo: LiveData<List<ShortMovieInfo>> = database.shortMovieInfoDao.getAllShortMovieInfo()

    suspend fun fetchMovieById(movieId: Int) {
        withContext(Dispatchers.IO) {
            lastMovieId = movieId
            val movie = RetrofitInstance.api.getMovieById(movieId.toString())
            database.movieDao.insertMovie(movie)
        }
    }

    suspend fun getMovieById(movieId: Int) : Movie {
        val movie:Movie
        withContext(Dispatchers.IO) {
            movie = database.movieDao.getMovieById(movieId)
        }
        return movie
    }

    val allMoviesData: LiveData<List<Movie>> = database.movieDao.getAllMovies()

    suspend fun insertFavoriteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            database.favoritesDao.insertFavoriteMovie(Favorites(movie.id!!))
            movie.isFavorite = true
            database.movieDao.insertMovie(movie)
        }
    }

    val allFavoriteMoviesInfo: LiveData<List<Favorites>> = database.favoritesDao.getAllFavoritesInfo()

    companion object {
        private const val TAG = "MoviesRepository"
    }
}