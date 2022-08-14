package com.test.watched.ui.movieslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.watched.R
import com.test.watched.data.datamodels.ShortMovieInfo
import com.test.watched.data.retrofit.RetrofitInstance
import com.test.watched.databinding.FragmentMoviesListBinding
import kotlinx.coroutines.launch

/**
 * Fragment containing a RecyclerView that displays a list of Movies
 */
class MoviesListFragment : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var moviesListAdapter: MoviesListAdapter
    private var currentPage = 0

    companion object {
        private const val TAG = "MoviesListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)

        moviesListAdapter = MoviesListAdapter(MoviesListAdapter.MoviesListItemListener {
            val movieId = it.id ?: -1
            Log.d(TAG, "onCreateView: move to $movieId")
            if (movieId != -1) {
                findNavController().navigate(MoviesListFragmentDirections.actionNavMoviesToMovieDetailsFragment(movieId))
            } else {
                Toast.makeText(context, "Invalid movieId $movieId", Toast.LENGTH_SHORT).show()
            }
        })

        binding.moviesListRecyclerView.adapter = moviesListAdapter

        lifecycleScope.launchWhenCreated { fetchItemsFromAPI() }

        // Make the list endless
        binding.moviesListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItems = lManager.itemCount
                val lastVisibleItem = lManager.findLastVisibleItemPosition()
                // Check if we should load new items
                val shouldLoad = totalItems > 1 && lastVisibleItem == (totalItems -1)
                if (shouldLoad) {
                    Log.d(TAG, "onCreateView load new items")
                    lifecycleScope.launch { fetchItemsFromAPI() }
                }
            }
        })

        return binding.root
    }

    private var moviesList = arrayListOf<ShortMovieInfo>()

    private suspend fun fetchItemsFromAPI() {
        // Max allowed by API is 1000
        if (currentPage > 1000) {
            Log.d(TAG, "fetchItemsFromAPI: max items loaded, returning")
            return
        }
        currentPage++

        val moviesResult = RetrofitInstance.api.getPopularMovies(page = currentPage.toString())
        val unfilteredMoviesList = moviesResult.results
        //val resultList: List<ShortMovieInfo>?

        // Force filter for tests, should add a check on Settings page to enable/disable this feature
        val shouldFilterAdult = true

        if (unfilteredMoviesList.isNotEmpty()) {
            val resultList = if (shouldFilterAdult) {
                val filteredList = unfilteredMoviesList.filter {
                    it.adult == false
                }
                moviesList + filteredList
            } else {
                moviesList + unfilteredMoviesList
            }
            moviesList = resultList as ArrayList<ShortMovieInfo>
            moviesListAdapter.submitList(moviesList)
        } else {
            // No movies found
            // Add a check if there is internet and if should load new movies
            // otherwise show an icon about no movies
        }

        Log.d(TAG, "onCreateView: list size after fetch from page $currentPage: ${moviesList.size} was filtered:$shouldFilterAdult")
    }
}