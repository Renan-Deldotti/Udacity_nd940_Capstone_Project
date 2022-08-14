package com.test.pokehelp.data.datamodels

import com.google.gson.annotations.SerializedName

data class MoviesList(
    @SerializedName("results")
    var results: ArrayList<ShortMovieInfo> = arrayListOf()
)

data class ShortMovieInfo(
    @SerializedName("adult") var adult: Boolean? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("original_title") var originalTitle: String? = null
)