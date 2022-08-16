package com.test.watched.ui.moviedetails

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.test.watched.R
import com.test.watched.data.datamodels.Movie
import com.test.watched.data.db.MovieDao
import com.test.watched.data.db.MoviesDatabase
import com.test.watched.data.db.MoviesRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val database = MoviesDatabase.getInstance(application)
    private val repository = MoviesRepository(database)

    private var _movieData: MutableLiveData<Movie> = MutableLiveData()

    val movieData: LiveData<Movie> get() = _movieData

    var buttonValue: Boolean = false

    private var _isFavorited: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorited: LiveData<Boolean> get() = _isFavorited

    fun getDataForId(movieId: Int) {
        viewModelScope.launch {
            try {
                repository.fetchMovieById(movieId)
            } catch (e:Exception) {
                Toast.makeText(getApplication(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            }
            _movieData.value = repository.getMovieById(movieId)
            _isFavorited.value = repository.getFavoritedMovieById(movieId) != null
        }
    }

    fun saveFavorite() {
        viewModelScope.launch {
            repository.insertFavoriteMovie(_movieData.value!!, buttonValue)
        }
    }
}