package com.test.watched.ui.favorites

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.watched.R
import com.test.watched.data.datamodels.ShortMovieInfo
import com.test.watched.databinding.SingleMovieItemBinding
import com.test.watched.utils.Constants

class FavoritesListAdapter(private val listener: FavoriteListItemListener) :
    ListAdapter<ShortMovieInfo, FavoritesListAdapter.FavoriteMovieItemViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieItemViewHolder {
        return FavoriteMovieItemViewHolder(
            SingleMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoriteMovieItemViewHolder, position: Int) {
        val favoriteMovieInfo = getItem(position)
        holder.itemView.setOnClickListener {
            listener.onFavoriteClicked(favoriteMovieInfo)
        }
        holder.bind(favoriteMovieInfo)
    }

    inner class FavoriteMovieItemViewHolder(private val itemBinding: SingleMovieItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(shortMovieInfo: ShortMovieInfo) {
            itemBinding.smiTitlePreview.text = shortMovieInfo.originalTitle ?: "No title"
            itemBinding.smiReleaseDatePreview.text = shortMovieInfo.releaseDate ?: "No date"
            val imagePath: String = Constants.API_IMAGE_BASE_URL + shortMovieInfo.backdropPath
            if (Constants.API_IMAGE_BASE_URL != shortMovieInfo.backdropPath) {
                Glide.with(itemBinding.smiPosterPreview)
                    .load(imagePath)
                    .placeholder(R.drawable.ic_movies)
                    .into(itemBinding.smiPosterPreview)
            } else {
                Log.d("MoviesListAdapter", "bind: backdropPath is empty, using placeholder image")
                itemBinding.smiPosterPreview.setImageDrawable(
                    AppCompatResources.getDrawable(
                        itemBinding.smiPosterPreview.context,
                        R.drawable.ic_movies
                    )
                )
            }
        }
    }

    companion object DiffCallBack : DiffUtil.ItemCallback<ShortMovieInfo>() {
        override fun areItemsTheSame(oldItem: ShortMovieInfo, newItem: ShortMovieInfo): Boolean {
            return oldItem.shortMovieId == newItem.shortMovieId
        }

        override fun areContentsTheSame(oldItem: ShortMovieInfo, newItem: ShortMovieInfo): Boolean {
            return oldItem == newItem
        }

    }

    class FavoriteListItemListener(val listener: (shortMovieInfo: ShortMovieInfo) -> Unit) {
        fun onFavoriteClicked(shortMovieInfo: ShortMovieInfo) = listener(shortMovieInfo)
    }
}