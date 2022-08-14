package com.test.pokehelp.data.retrofit

import com.test.pokehelp.data.datamodels.Movie
import com.test.pokehelp.data.datamodels.MoviesList
import com.test.pokehelp.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object RetrofitInstance {

    val api: FetchAPI by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchAPI::class.java)
    }
}

interface FetchAPI {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key:String = Constants.API_KEY,
        @Query("language") language: String = "en-US",
        @Query("page") page: String = "1"
    ) : MoviesList

    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey:String = Constants.API_KEY,
        @Query("language") language: String = "en-US"
    ) : Movie

}