package com.example.omar_salem.mymovie;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.omar_salem.mymovie.adapter.MoviesAdapter;
import com.example.omar_salem.mymovie.apis.Clients;
import com.example.omar_salem.mymovie.apis.Loading;
import com.example.omar_salem.mymovie.model.Movie;
import com.example.omar_salem.mymovie.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView recyclerView;
    private MoviesAdapter adapter;
    private List<Movie> MovieList;
    private ProgressDialog dialog;
  private SwipeRefreshLayout refreshLayout;
    public static  final  String LOG_TAG=MoviesAdapter.class.getName();

   // String getStringKey=getString(R.string.sorting_Poupelr);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Views();

    }
    private void Views()
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
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));}
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));}
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
       // LoadFromService();
        CheckSorted();

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
   private void  LoadFromService(){

       try {
           if(BuildConfig.theMovieApi.isEmpty())
           {
               Toast.makeText(getApplicationContext(),"Check Your APi ",Toast.LENGTH_SHORT).show();
               dialog.dismiss();
               return;
           }


           Clients clients=new Clients();
           Loading apploading=clients.getClint().create(Loading.class);
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
           });
       }catch (Exception e){
           Log.d("Error in Try Block",e.getMessage());
           Toast.makeText(MainActivity.this,"Error in try block",Toast.LENGTH_SHORT).show();
       }


   }
    private void  LoadFromService2(){

        try {
            if(BuildConfig.theMovieApi.isEmpty())
            {
                Toast.makeText(getApplicationContext(),"Check Your APi ",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                return;
            }


            Clients clients=new Clients();
            Loading apploading=clients.getClint().create(Loading.class);
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
        switch (item.getItemId())
        {
            case R.id.menu_settings:
                Intent i = new Intent(this,Sittings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
Log.d(LOG_TAG,"prefrence Updated ");
        CheckSorted();
    }
    private void  CheckSorted ()
    {
        SharedPreferences Preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String Sort=Preferences.getString(
                this.getString(R.string.sorting_key),
                this.getString(R.string.sorting_Poupelr)
        );
        if(Sort.equals(this.getString(R.string.sorting_Poupelr)))
        {
            Log.d(LOG_TAG,"Sorting by Poupelar ");
            LoadFromService();
        }
        else {Log.d(LOG_TAG,"Sorting by Rating");
            LoadFromService2();}
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MovieList.isEmpty())
        {
            CheckSorted();
        }
        else
        {

        }
    }
}
