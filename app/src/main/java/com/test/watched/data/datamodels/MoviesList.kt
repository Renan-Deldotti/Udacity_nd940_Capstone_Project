package com.test.watched.data.datamodels

import com.google.gson.annotations.SerializedName

/**
 * Holds the results containing a list of short movies description
 */
data class MoviesList(
    @SerializedName("results")
    var results: ArrayList<ShortMovieInfo> = arrayListOf()
)

data class ShortMovieInfo(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("release_date") var releaseDate: String? = null
)
