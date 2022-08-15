package com.test.watched.data.datamodels

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun convertGenresListToJson(genresList: ArrayList<Genres>): String {
        val type = object : TypeToken<ArrayList<Genres>>() {}.type
        return Gson().toJson(genresList, type)
    }

    @TypeConverter
    fun convertJsonToGenresList(json: String): ArrayList<Genres> {
        val type = object : TypeToken<ArrayList<Genres>>() {}.type
        return Gson().fromJson(json, type) as ArrayList<Genres>
    }

    @TypeConverter
    fun convertProductionCompaniesListToJson(companiesList: ArrayList<ProductionCompanies>) : String {
        val type = object : TypeToken<ArrayList<ProductionCompanies>>() {}.type
        return Gson().toJson(companiesList, type)
    }

    @TypeConverter
    fun convertJsonToProductionCompaniesList(json: String): ArrayList<ProductionCompanies> {
        val type = object : TypeToken<ArrayList<ProductionCompanies>>() {}.type
        return Gson().fromJson(json, type) as ArrayList<ProductionCompanies>
    }
}