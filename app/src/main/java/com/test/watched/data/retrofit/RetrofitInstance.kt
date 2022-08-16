package com.test.watched.data.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.test.watched.data.datamodels.Movie
import com.test.watched.data.datamodels.MoviesList
import com.test.watched.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object RetrofitInstance {

    private val gson: Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()

    val api: FetchAPI by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(FetchAPI::class.java)
    }
}

interface FetchAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1"
    ): MoviesList

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("language") language: String = "en-US"
    ): Movie

    // To be used with NotificationWork
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") api_key: String = Constants.API_KEY
    ) : MoviesList
}