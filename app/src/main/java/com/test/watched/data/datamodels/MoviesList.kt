package com.test.watched.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Holds the results containing a list of short movies description
 */
data class MoviesList(
    @SerializedName("results")
    var results: ArrayList<ShortMovieInfo> = arrayListOf()
)

@Entity(tableName = "short_movie_info_table")
data class ShortMovieInfo(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("id") @PrimaryKey() var id: Int? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null
)
