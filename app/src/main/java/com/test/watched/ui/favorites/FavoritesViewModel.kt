package com.test.watched.ui.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.test.watched.data.datamodels.Favorites
import com.test.watched.data.db.MoviesDatabase
import com.test.watched.data.db.MoviesRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val database = MoviesDatabase.getInstance(application)
    private val repository = MoviesRepository(database)

    val favoritesMovies: LiveData<List<Favorites>> = repository.allFavoriteMoviesInfo
}