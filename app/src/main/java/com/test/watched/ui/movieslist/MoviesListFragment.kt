package com.test.watched.ui.movieslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.watched.databinding.FragmentMoviesListBinding

/**
 * Fragment containing a RecyclerView that displays a list of Movies
 */
class MoviesListFragment : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var moviesListAdapter: MoviesListAdapter
    private val viewModel: MoviesListViewModel by viewModels()

    companion object {
        private const val TAG = "MoviesListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)

        moviesListAdapter = MoviesListAdapter(MoviesListAdapter.MoviesListItemListener {
            val movieId = it.shortMovieId ?: -1
            Log.d(TAG, "onCreateView: move to $movieId")
            if (movieId != -1) {
                findNavController().navigate(MoviesListFragmentDirections.actionNavMoviesToMovieDetailsFragment(movieId))
            } else {
                Toast.makeText(context, "Invalid movieId $movieId", Toast.LENGTH_SHORT).show()
            }
        })

        binding.moviesListRecyclerView.adapter = moviesListAdapter

        viewModel.shortMovieInfos.observe(viewLifecycleOwner) {
            moviesListAdapter.submitList(it)
        }

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
                    // The next page should be calculated by dividing the current amount
                    // by the number of items per request
                    val currentPage = totalItems / 20
                    val nextPage = currentPage + 1
                    Log.d(TAG, "onScrolled load new items for page: $nextPage")
                    viewModel.loadNewMovieShortInfo(nextPage)
                }
            }
        })

        return binding.root
    }
}