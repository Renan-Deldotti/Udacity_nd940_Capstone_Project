package com.test.watched.data.datamodels

import com.google.gson.annotations.SerializedName

/**
 * Provide info about a single Movie item
 */
data class Movie(
    @SerializedName("genres") var genres: ArrayList<Genres> = arrayListOf(),
    @SerializedName("id") var id: Int? = null,
    @SerializedName("imdb_id") var imdbId: String? = null,
    @SerializedName("original_title") var originalTitle: String? = null,
    @SerializedName("overview") var overview: String? = null,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("production_companies") var productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
    @SerializedName("release_date") var releaseDate: String? = null,
    @SerializedName("runtime") var runtime: Int? = null,
    @SerializedName("tagline") var tagline: String? = null
)

/**
 * Provide the genres of a Moview item
 */
data class Genres(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null
)

/**
 * Provide info about the Company that made the movie
 */
data class ProductionCompanies(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("logo_path") var logoPath: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("origin_country") var originCountry: String? = null
)