package com.test.watched.ui.movieslist

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.test.watched.R
import com.test.watched.data.datamodels.ShortMovieInfo
import com.test.watched.data.db.ShortMovieInfoDatabase
import com.test.watched.data.db.ShortMovieRepository
import com.test.watched.utils.Constants
import com.test.watched.utils.getAppSharedPreferences
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviesListViewModel(application: Application) : AndroidViewModel(application) {

    private val database = ShortMovieInfoDatabase.getInstance(application)
    private val repository = ShortMovieRepository(database)
    private var filterResults = true

    init {
        viewModelScope.launch {
            try {
                filterResults = application.getAppSharedPreferences().getBoolean(Constants.FILTER_ADULT_MOVIES_PREF_KEY, true)
                repository.fetchMoviesFromApi(1, filterResults)
            } catch (e: Exception) {
                Toast.makeText(application.applicationContext, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loadNewMovieShortInfo(page: Int) {
        viewModelScope.launch {
            repository.fetchMoviesFromApi(page, filterResults)
        }
    }

    val shortMovieInfos: LiveData<List<ShortMovieInfo>> = repository.allShortMovieInfo
}