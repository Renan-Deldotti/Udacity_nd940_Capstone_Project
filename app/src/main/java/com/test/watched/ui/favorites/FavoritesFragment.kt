package com.test.watched.ui.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.test.watched.R
import com.test.watched.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesListAdapter: FavoritesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        favoritesListAdapter = FavoritesListAdapter(FavoritesListAdapter.FavoriteListItemListener {
            // As all data from favorites is saved we can allow the user to access the DetailsFragment
            findNavController().navigate(FavoritesFragmentDirections.actionNavFavoritesToNavDetails(it.shortMovieId!!))
        })

        binding.favoritesListRecyclerView.adapter = favoritesListAdapter

        viewModel.favoritesMovies.observe(viewLifecycleOwner) { favoriteList ->
            val idsList: List<Int> = favoriteList.map {
                it.shortMovieInfoId
            }
            viewModel.favoritesMoviesShortInfo(*idsList.toIntArray()).observe(viewLifecycleOwner) { shortMovieInfoList ->
                if (shortMovieInfoList.isNotEmpty()) {
                    favoritesListAdapter.submitList(shortMovieInfoList)
                    binding.favoritesListEmptyImageVIew.visibility = View.GONE
                    binding.favoritesListEmptyTextView.visibility = View.GONE
                } else {
                    binding.favoritesListEmptyImageVIew.visibility = View.VISIBLE
                    binding.favoritesListEmptyTextView.visibility = View.VISIBLE
                }
            }
        }


        return binding.root
    }

    companion object {
        private const val TAG = "FavoritesFragment"
    }
}