package com.test.watched.ui.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.test.watched.R
import com.test.watched.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        viewModel.favoritesMovies.observe(viewLifecycleOwner) { it ->
            it.forEach {
                Log.d(TAG, "onCreateView: $it")
            }
        }

        return binding.root
    }

    companion object {
        private const val TAG = "FavoritesFragment"
    }
}