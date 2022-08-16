package com.test.watched.data.datamodels

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies_table", indices = arrayOf(Index(value = ["shortMovieInfoId"], unique = true)))
data class Favorites (
    val shortMovieInfoId: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}