package com.example.omar_salem.mymovie.apis;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Omar_Salem on 9/7/2017.
 */

public class Clients {
    private static final String Base_URL ="http://api.themoviedb.org/3/";
    public static Retrofit retrofit=null;
    public  static Retrofit getClint()
    {

        if(retrofit== null)
        {
            retrofit=new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
