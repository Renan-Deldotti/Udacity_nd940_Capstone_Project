package com.test.watched.ui.moviedetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.test.watched.R
import com.test.watched.databinding.FragmentMovieDetailsBinding


class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val mdfArgs: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        val movieId = mdfArgs.movieId
        Log.d(TAG, "onCreateView: movieId= $movieId")

        binding.movieDetailsReleaseDate.text = resources.getString(R.string.release_date_w_colon, "01-01-2000")

        return binding.root
    }

    companion object {
        private const val TAG = "MovieDetailsFragment"
    }
}