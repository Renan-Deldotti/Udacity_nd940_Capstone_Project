package com.test.watched.ui.movieslist

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.test.watched.R
import com.test.watched.data.datamodels.ShortMovieInfo
import com.test.watched.data.db.MoviesDatabase
import com.test.watched.data.db.MoviesRepository
import com.test.watched.utils.Constants
import com.test.watched.utils.getAppSharedPreferences
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MoviesDatabase.getInstance(application)
    private val repository = MoviesRepository(database)
    private var filterResults = true

    init {
        filterResults = application.getAppSharedPreferences().getBoolean(Constants.FILTER_ADULT_MOVIES_PREF_KEY, true)
        loadNewMovieShortInfo(1)
    }

    fun loadNewMovieShortInfo(page: Int) {
        viewModelScope.launch {
            try {
                repository.fetchMoviesFromApi(page, filterResults)
            } catch (e: Exception) {
                Log.d(TAG, "No internet connection")
            }
        }
    }

    val shortMovieInfos: LiveData<List<ShortMovieInfo>> = repository.allShortMovieInfo

    companion object {
        private const val TAG = "MoviesListViewModel"
    }
}