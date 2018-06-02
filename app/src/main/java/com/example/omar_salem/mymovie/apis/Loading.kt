package com.example.omar_salem.mymovie.apis

import com.example.omar_salem.mymovie.model.MovieResponse

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Omar_Salem on 9/7/2017.
 */

interface Loading {
    @GET("movie/popular")
    fun getpopularMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

    @GET("movie/top_rated")
    fun getTopMovies(@Query("api_key") apiKey: String): Call<MovieResponse>

}
