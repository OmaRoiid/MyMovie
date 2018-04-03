package com.example.omar_salem.mymovie;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.omar_salem.mymovie.adapter.MoviesAdapter;
import com.example.omar_salem.mymovie.apis.RetrofitClient;
import com.example.omar_salem.mymovie.apis.Loading;
import com.example.omar_salem.mymovie.data.FavoriteDbHelper;
import com.example.omar_salem.mymovie.model.Movie;
import com.example.omar_salem.mymovie.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnSharedPreferenceChangeListener {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> MovieList;
    private ProgressDialog dialog;
    private FavoriteDbHelper favoriteDbHelper;
    public static  final  String LOG_TAG=MoviesAdapter.class.getName();
    private final String PopularMovies="Popular Movies";
    private final String TopRatedMovies="TopRated Movies";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupViews();

    }
    private void SetupViews()
    {
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.setCancelable(false);
        dialog.show();
        recyclerView=(RecyclerView)findViewById(R.id.res_id);
        MovieList=new ArrayList<>();
        adapter=new MoviesAdapter(this,MovieList);
        if(getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)//this if  layout in oriantation or landskaep
        {
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        LoadMovies(PopularMovies);


    }

  private void SetupFavorite(){
    recyclerView=(RecyclerView)findViewById(R.id.res_id);
    MovieList=new ArrayList<>();
    adapter=new MoviesAdapter(this,MovieList);
    if(getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)//this if  layout in oriantation or landskaep
    {
      recyclerView.setLayoutManager(new GridLayoutManager(this,2));
    }
    else{
      recyclerView.setLayoutManager(new GridLayoutManager(this,4));
    }
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.setAdapter(adapter);
    adapter.notifyDataSetChanged();
    favoriteDbHelper =new FavoriteDbHelper(this);
    getAllFavoritFromDB();

  }


  public Activity getActivity()
    {
        Context context=this;
        while(context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity) context;
            }
            context=((ContextWrapper) context).getBaseContext();
        }

        return null;
    }
   private void  LoadMovies( String check){

       try {
           if(BuildConfig.theMovieApi.isEmpty())
           {
               Toast.makeText(getApplicationContext(),"Check Your APi ",Toast.LENGTH_SHORT).show();
               dialog.dismiss();
               return;
           }


           RetrofitClient clients=new RetrofitClient();
           Loading apploading=clients.getClint().create(Loading.class);
           //Fetch PopularMovies from api
          if(check.equals(PopularMovies))
          {
           Call<MovieResponse> call=apploading.getpopularMovies(BuildConfig.theMovieApi);
           call.enqueue(new Callback<MovieResponse>() {
               @Override
               public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                   List <Movie> movies= response.body().getResults();
                   recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(),movies));
                   recyclerView.smoothScrollToPosition(0);
                   dialog.dismiss();
               }

               @Override
               public void onFailure(Call<MovieResponse> call, Throwable t) {
                 Log.d("Error ",t.getMessage());
                   dialog.dismiss();
                   Toast.makeText(MainActivity.this,"Error to Get Data ",Toast.LENGTH_SHORT).show();


               }
           });}
           else //Fetch Toprated Movies from api
             {
            Call<MovieResponse> call=apploading.getTopMovies(BuildConfig.theMovieApi);
            call.enqueue(new Callback<MovieResponse>() {
              @Override
              public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List <Movie> movies= response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(getApplicationContext(),movies));
                recyclerView.smoothScrollToPosition(0);

                dialog.dismiss();
              }

              @Override
              public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.d("Error ",t.getMessage());
                dialog.dismiss();
                Toast.makeText(MainActivity.this,"Error to Get Data ",Toast.LENGTH_SHORT).show();


              }
            });
          }
       }catch (Exception e){
           Log.d("Error in Try Block",e.getMessage());
           Toast.makeText(MainActivity.this,"Error in try block",Toast.LENGTH_SHORT).show();
       }


   }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if (getActionBar() != null) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
      }
        switch (item.getItemId())
        {
            case R.id.action_popular:
               LoadMovies(PopularMovies);

                return true;
            case R.id.action_top:
                LoadMovies(TopRatedMovies);
                break;
          case R.id.action_favorite:
            SetupFavorite();
            break;

        }
        return super.onOptionsItemSelected(item);
    }

  private void getAllFavoritFromDB() {
    new AsyncTask<Void, Void, Void>() {
      @Override
      protected Void doInBackground(Void... voids) {
        MovieList.clear();
        MovieList.addAll(favoriteDbHelper.getAll());
        return null;
      }

      @Override
      protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.notifyDataSetChanged();
      }
    }.execute();
  }


  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

  }
}
