package com.test.watched.ui.moviedetails

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.color.MaterialColors
import com.test.watched.R
import com.test.watched.data.datamodels.Movie
import com.test.watched.data.retrofit.RetrofitInstance
import com.test.watched.databinding.FragmentMovieDetailsBinding
import com.test.watched.utils.Constants
import kotlinx.coroutines.flow.collect


class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val mdfArgs: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        val movieId = mdfArgs.movieId
        viewModel.getDataForId(movieId)

        viewModel.movieData.observe(viewLifecycleOwner) {
            setMovieData(it)
        }

        return binding.root
    }

    private fun setMovieData(data: Movie) {
        val posterUrl = Constants.API_IMAGE_BASE_URL + data.posterPath
        Log.d(TAG, "movieData for ${data.id} $posterUrl")

        if (Constants.API_IMAGE_BASE_URL != posterUrl) {
            Glide.with(binding.movieDetailsPoster)
                .load(posterUrl)
                .placeholder(R.drawable.ic_movies)
                .into(binding.movieDetailsPoster)
        } else {
            binding.movieDetailsPoster.setImageDrawable(AppCompatResources.getDrawable(binding.movieDetailsPoster.context, R.drawable.ic_movies))
        }

        binding.movieDetailsFavoriteButton.isEnabled = false
        viewModel.isFavorited.observe(viewLifecycleOwner) {
            Log.d(TAG, "setMovieData: is favorite: $it")
            binding.movieDetailsFavoriteButton.imageTintList = if (it) {
                ColorStateList.valueOf(resources.getColor(R.color.checked_favorite_button, null))
            } else {
                ColorStateList.valueOf(resources.getColor(R.color.unchecked_favorite_button, null))
            }

            viewModel.buttonValue = it

            binding.movieDetailsFavoriteButton.setOnClickListener {
                viewModel.buttonValue = !viewModel.buttonValue
                viewModel.saveFavorite()
                binding.movieDetailsFavoriteButton.imageTintList = if (viewModel.buttonValue) {
                    ColorStateList.valueOf(resources.getColor(R.color.checked_favorite_button, null))
                } else {
                    ColorStateList.valueOf(resources.getColor(R.color.unchecked_favorite_button, null))
                }
            }

            binding.movieDetailsFavoriteButton.isEnabled = true
            binding.movieDetailsFavoriteButton.isVisible = true
        }

        // Some movies does not have the data set, so just put an empty string
        binding.movieDetailsTitle.text = data.originalTitle ?: getString(R.string.no_title)
        binding.movieDetailsTagline.text = data.tagline ?: ""
        binding.movieDetailsOverview.text = data.overview ?: ""
        binding.movieDetailsReleaseDate.text = getString(R.string.release_date_w_colon,  data.releaseDate ?: "")
        binding.movieDetailsRuntime.text = getString(R.string.runtime_w_colon, data.runtime ?: "")

        var genresFormattedString = ""
        data.genres.forEach {
            genresFormattedString += it.name + "\n"
        }
        if (!TextUtils.isEmpty(genresFormattedString)) {
            binding.movieDetailsGenresLabel.visibility = View.VISIBLE
            binding.movieDetailsGenresValues.text = genresFormattedString
        }

        var producedByFormattedString = ""
        data.productionCompanies.forEach {
            producedByFormattedString += it.name + "\n"
        }
        if (!TextUtils.isEmpty(producedByFormattedString)) {
            binding.movieDetailsProducedByLabel.visibility = View.VISIBLE
            binding.movieDetailsProducedByValues.text = producedByFormattedString
        }
    }

    companion object {
        private const val TAG = "MovieDetailsFragment"
    }
}