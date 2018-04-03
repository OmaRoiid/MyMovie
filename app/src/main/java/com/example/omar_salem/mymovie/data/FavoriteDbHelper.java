package com.example.omar_salem.mymovie.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.omar_salem.mymovie.data.Favorite.FavoriteTable;
import com.example.omar_salem.mymovie.model.Movie;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Add class header
 */

public class FavoriteDbHelper extends SQLiteOpenHelper {
  private final static String DATABASE_NAME="favorite.db";
  private final static int DATABASE_VERSION=1;
  SQLiteOpenHelper openHelper;
  SQLiteDatabase sqLiteDatabase;

  public FavoriteDbHelper(Context context)
  {
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    final String CREATE_FAVORITE_TABLE="CREATE TABLE "+ FavoriteTable.TABLE_NAME+"("+
        FavoriteTable._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
        FavoriteTable.MOVIEID+"INTEGER," +
        FavoriteTable.TITTLE+"TEXT NOT NULL,"+
        FavoriteTable.USER_RATE+"REAL NOT NULL,"+
        FavoriteTable.POSTER_PATH+"TEXT NOT NULL,"+
        FavoriteTable.MOVIE_OVERVIEW+"TEXT NOT NULL"+
        ");";
    db.execSQL(CREATE_FAVORITE_TABLE);

  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }
  public void AddToFavorite(Movie movie)
  {
    SQLiteDatabase database=this.getWritableDatabase();
    ContentValues values= new ContentValues();
    values.put(FavoriteTable.MOVIEID,movie.getId());
    values.put(FavoriteTable.TITTLE,movie.getOriginal_title());
    values.put(FavoriteTable.USER_RATE,movie.getVote_average());
    values.put(FavoriteTable.POSTER_PATH,movie.getPosterPath());
    values.put(FavoriteTable.MOVIE_OVERVIEW,movie.getOverview());
database.insert(FavoriteTable.TABLE_NAME,null,values);
database.close();
  }
  public void DeletFromFavorite(int id)
  {
    SQLiteDatabase database=this.getWritableDatabase();
    database.delete(FavoriteTable.TABLE_NAME,FavoriteTable.MOVIEID+"= " +id,null);
  }

  public List<Movie> getAll(){
  String [] Columns={
      FavoriteTable._ID,
         FavoriteTable.MOVIEID,
          FavoriteTable.TITTLE,
          FavoriteTable.USER_RATE,
          FavoriteTable.POSTER_PATH,
          FavoriteTable.MOVIE_OVERVIEW
  };
  String SortOrder=FavoriteTable._ID+ " ASC";
    List<Movie> FavoritList=new ArrayList<>();
    SQLiteDatabase db=this.getReadableDatabase();
    Cursor Dbcursor= db.query(FavoriteTable.TABLE_NAME,
        Columns,null,null,null,null,SortOrder);
    if (Dbcursor.moveToFirst()){
      do{
        Movie MovieToDB=new Movie();
          MovieToDB.setId(Integer.parseInt(Dbcursor.getString(Dbcursor.getColumnIndex(FavoriteTable.MOVIEID))));
        MovieToDB.setOriginal_title(Dbcursor.getString(Dbcursor.getColumnIndex(FavoriteTable.TITTLE)));
        MovieToDB.setVote_average(Double.valueOf(Dbcursor.getString(Dbcursor.getColumnIndex(FavoriteTable.USER_RATE))));
        MovieToDB.setPosterPath(Dbcursor.getString(Dbcursor.getColumnIndex(FavoriteTable.POSTER_PATH)));
        MovieToDB.setOverview(Dbcursor.getString(Dbcursor.getColumnIndex(FavoriteTable.MOVIE_OVERVIEW)));
        FavoritList.add(MovieToDB);
      }while (Dbcursor.moveToNext());
    }
    Dbcursor.close();
    db.close();
    return FavoritList;
}
}
