package com.test.watched.data.datamodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Holds the results containing a list of short movies description
 */
data class MoviesList(
    @SerializedName("results")
    @Expose
    var results: ArrayList<ShortMovieInfo> = arrayListOf()
)

@Entity(tableName = "short_movie_info_table")
data class ShortMovieInfo(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @SerializedName("adult") @Expose var adult: Boolean? = null,
    @SerializedName("backdrop_path") @Expose var backdropPath: String? = null,
    @SerializedName("id") @Expose var shortMovieId: Int? = null,
    @SerializedName("original_title") @Expose var originalTitle: String? = null,
    @SerializedName("release_date") @Expose var releaseDate: String? = null
)
