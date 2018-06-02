package com.example.omar_salem.mymovie.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Omar_Salem on 9/7/2017.
 */

object RetrofitClient {
    private val Base_URL = "http://api.themoviedb.org/3/"
    var retrofit: Retrofit? = null
    val clint: Retrofit
        get() {

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(Base_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

            }
            return retrofit!!
        }
}
