package com.example.omar_salem.mymovie.ui


import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.Configuration
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.omar_salem.mymovie.BuildConfig
import com.example.omar_salem.mymovie.R

import com.example.omar_salem.mymovie.adapter.MoviesAdapter
import com.example.omar_salem.mymovie.apis.RetrofitClient
import com.example.omar_salem.mymovie.apis.Loading
import com.example.omar_salem.mymovie.data.FavoriteDbHelper
import com.example.omar_salem.mymovie.model.Movie
import com.example.omar_salem.mymovie.model.MovieResponse

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {
    private var recyclerView: RecyclerView? = null
    private var adapter: MoviesAdapter? = null
    private var MovieList: MutableList<Movie>? = null
    private var dialog: ProgressDialog? = null
    private var favoriteDbHelper: FavoriteDbHelper? = null
    private val PopularMovies = "Popular Movies"
    private val TopRatedMovies = "TopRated Movies"


    val activity: Activity?
        get() {
            var context: Context = this
            while (context is ContextWrapper) {
                if (context is Activity) {
                    return context
                }
                context = context.baseContext
            }

            return null
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SetupViews()

    }

    private fun SetupViews() {
        dialog = ProgressDialog(this)
        dialog!!.setMessage("Loading....")
        dialog!!.setCancelable(false)
        dialog!!.show()
        recyclerView = findViewById(R.id.res_id) as RecyclerView
        MovieList = ArrayList()
        adapter = MoviesAdapter(this, MovieList!!)
        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT)
        //this if  layout in oriantation or landskaep
        {
            recyclerView!!.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView!!.layoutManager = GridLayoutManager(this, 4)
        }

        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
        LoadMovies(PopularMovies)


    }

    private fun SetupFavorite() {
        recyclerView = findViewById(R.id.res_id) as RecyclerView
        MovieList = ArrayList()
        adapter = MoviesAdapter(this, MovieList!!)
        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT)
        //this if  layout in oriantation or landskaep
        {
            recyclerView!!.layoutManager = GridLayoutManager(this, 2)
        } else {
            recyclerView!!.layoutManager = GridLayoutManager(this, 4)
        }
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
        favoriteDbHelper = FavoriteDbHelper(this)
        getAllFavoritFromDB()

    }

    private fun LoadMovies(check: String) {

        try {
            if (BuildConfig.theMovieApi.isEmpty()) {
                Toast.makeText(applicationContext, "Check Your APi ", Toast.LENGTH_SHORT).show()
                dialog!!.dismiss()
                return
            }


            val clients = RetrofitClient
            val apploading = clients.clint.create(Loading::class.java)
            //Fetch PopularMovies from api
            if (check == PopularMovies) {
                val call = apploading.getpopularMovies(BuildConfig.theMovieApi)
                call.enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        val movies = response.body()!!.results
                        recyclerView!!.adapter = MoviesAdapter(applicationContext, movies)
                        recyclerView!!.smoothScrollToPosition(0)
                        dialog!!.dismiss()
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d("Error ", t.message)
                        dialog!!.dismiss()
                        Toast.makeText(this@MainActivity, "Error to Get Data ", Toast.LENGTH_SHORT).show()


                    }
                })
            } else
            //Fetch Toprated Movies from api
            {
                val call = apploading.getTopMovies(BuildConfig.theMovieApi)
                call.enqueue(object : Callback<MovieResponse> {
                    override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                        val movies = response.body()!!.results
                        recyclerView!!.adapter = MoviesAdapter(applicationContext, movies)
                        recyclerView!!.smoothScrollToPosition(0)

                        dialog!!.dismiss()
                    }

                    override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                        Log.d("Error ", t.message)
                        dialog!!.dismiss()
                        Toast.makeText(this@MainActivity, "Error to Get Data ", Toast.LENGTH_SHORT).show()


                    }
                })
            }
        } catch (e: Exception) {
            Log.d("Error in Try Block", e.message)
            Toast.makeText(this@MainActivity, "Error in try block", Toast.LENGTH_SHORT).show()
        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBar != null) {
            actionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        when (item.itemId) {
            R.id.action_popular -> {
                LoadMovies(PopularMovies)

                return true
            }
            R.id.action_top -> LoadMovies(TopRatedMovies)
            R.id.action_favorite -> SetupFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getAllFavoritFromDB() {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                MovieList!!.clear()
                MovieList!!.addAll(favoriteDbHelper!!.all)
                return null
            }

            override fun onPostExecute(aVoid: Void) {
                super.onPostExecute(aVoid)
                adapter!!.notifyDataSetChanged()
            }
        }.execute()
    }


    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

    }

    companion object {
        val LOG_TAG = MoviesAdapter::class.java.name
    }
}
