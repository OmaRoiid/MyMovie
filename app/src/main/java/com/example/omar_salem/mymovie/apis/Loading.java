package com.example.omar_salem.mymovie.apis;

import com.example.omar_salem.mymovie.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Omar_Salem on 9/7/2017.
 */

public interface Loading {
    @GET("movie/popular")
    Call<MovieResponse> getpopularMovies(@Query("api_key") String apiKey);
    @GET("movie/top_rated")
    Call<MovieResponse> getTopMovies(@Query("api_key") String apiKey);

}
