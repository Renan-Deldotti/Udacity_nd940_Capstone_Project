package com.test.watched.ui.movieslist

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.test.watched.MainActivity
import com.test.watched.R
import com.test.watched.databinding.FragmentMoviesListBinding
import com.test.watched.utils.connectivityManager
import com.test.watched.utils.hasInternetConnectivity

/**
 * Fragment containing a RecyclerView that displays a list of Movies
 */
class MoviesListFragment : Fragment() {

    private lateinit var binding: FragmentMoviesListBinding
    private lateinit var moviesListAdapter: MoviesListAdapter
    private val viewModel: MoviesListViewModel by viewModels()
    private var nextPage: Int = 1

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
            if (requireContext().hasInternetConnectivity()) {
                Log.d(TAG, "onCreateView: move to $movieId")
                if (movieId != -1) {
                    findNavController().navigate(MoviesListFragmentDirections.actionNavMoviesToMovieDetailsFragment(movieId))
                } else {
                    Toast.makeText(context, "Invalid movieId $movieId", Toast.LENGTH_SHORT).show()
                }
            } else {
                // No data connectivity show the snackbar to let user know about internet
                Snackbar.make(binding.root, R.string.no_internet_connection, Snackbar.LENGTH_LONG).apply {
                    setAction(R.string.turn_on){
                        startActivity(Intent().apply {
                            action = Settings.ACTION_WIFI_SETTINGS
                        })
                    }
                }.show()
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
                    nextPage = currentPage + 1
                    viewModel.loadNewMovieShortInfo(nextPage)
                }
            }
        })

        listenForNwChanges()

        return binding.root
    }

    private fun listenForNwChanges() {
        // The user may change the connectivity through the notifications bar
        // so we listen for this changes to adapt to all connectivity changes
        val networkRequest = NetworkRequest.Builder().apply {
            addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
        }.build()

        requireContext().connectivityManager.requestNetwork(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                if (nextPage == 1) {
                    // Only start the query if the list is empty
                    viewModel.loadNewMovieShortInfo(nextPage)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
            }
        })
    }
}