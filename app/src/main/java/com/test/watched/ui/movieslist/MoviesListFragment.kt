package com.test.watched.ui.movieslist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.test.watched.R
import com.test.watched.data.retrofit.RetrofitInstance
import com.test.watched.databinding.FragmentMoviesListBinding

/**
 * Fragment containing a RecyclerView that displays a list of Moview
 */
class MoviesListFragment : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var moviesListAdapter: MoviesListAdapter

    companion object {
        private const val TAG = "MoviesListFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)

        moviesListAdapter = MoviesListAdapter(MoviesListAdapter.MoviesListItemListener {
            Log.d(TAG, "onCreateView: should move to ${it.originalTitle}")
        })

        binding.moviesListRecyclerView.adapter = moviesListAdapter

        lifecycleScope.launchWhenCreated { testAdapter() }

        return binding.root
    }

    private suspend fun testAdapter() {
        val t = RetrofitInstance.api.getPopularMovies(page = "1")
        Log.d(TAG, "onCreateView: testAdapter ${t.results}")
        moviesListAdapter.submitList(t.results)
    }
}