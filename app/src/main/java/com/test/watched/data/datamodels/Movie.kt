package com.test.watched.data.datamodels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Provide info about a single Movie item
 */
data class Movie(
    @SerializedName("genres") @Expose var genres: ArrayList<Genres> = arrayListOf(),
    @SerializedName("id") @Expose var id: Int? = null,
    @SerializedName("imdb_id") @Expose var imdbId: String? = null,
    @SerializedName("original_title") @Expose var originalTitle: String? = null,
    @SerializedName("overview") @Expose var overview: String? = null,
    @SerializedName("poster_path") @Expose var posterPath: String? = null,
    @SerializedName("production_companies") @Expose var productionCompanies: ArrayList<ProductionCompanies> = arrayListOf(),
    @SerializedName("release_date") @Expose var releaseDate: String? = null,
    @SerializedName("runtime") @Expose var runtime: Int? = null,
    @SerializedName("tagline") @Expose var tagline: String? = null
)

/**
 * Provide the genres of a Moview item
 */
data class Genres(
    @SerializedName("id") @Expose var id: Int? = null,
    @SerializedName("name") @Expose var name: String? = null
)

/**
 * Provide info about the Company that made the movie
 */
data class ProductionCompanies(
    @SerializedName("id") @Expose var id: Int? = null,
    @SerializedName("logo_path") @Expose var logoPath: String? = null,
    @SerializedName("name") @Expose var name: String? = null,
    @SerializedName("origin_country") @Expose var originCountry: String? = null
)
