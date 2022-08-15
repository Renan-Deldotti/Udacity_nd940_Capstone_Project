package com.test.watched.data.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.test.watched.data.datamodels.ShortMovieInfo
import com.test.watched.data.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepository(private val database: MoviesDatabase) {

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

    companion object {
        private const val TAG = "MoviesRepository"
    }
}