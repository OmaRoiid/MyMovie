package com.example.omar_salem.mymovie.data;

import android.provider.BaseColumns;

/**
 * TODO: Add class header
 */

public class Favorite {
  public static final class FavoriteTable implements BaseColumns{

    public static final String TABLE_NAME="favorite";
    public static final String MOVIEID="movieid";
    public static final String TITTLE="tittle";
    public static final String USER_RATE="user_rate";
    public static final String POSTER_PATH="poster_path";
    public static final String MOVIE_OVERVIEW="overview";
  }

}
