package com.example.omar_salem.mymovie.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import com.bumptech.glide.Glide
import com.example.omar_salem.mymovie.ui.DetailActivity
import com.example.omar_salem.mymovie.R
import com.example.omar_salem.mymovie.model.Movie



class MoviesAdapter(private val mContext: Context, private val mMovieList: List<Movie>) : RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesAdapter.MyViewHolder {
        val mV = LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_card, parent, false)
        return MyViewHolder(mV)
    }

    override fun onBindViewHolder(holder: MoviesAdapter.MyViewHolder, position: Int) {
        holder.tittle.text = mMovieList[position].original_title
        val voteuser = java.lang.Double.toString(mMovieList[position].vote_average!!)
        holder.userratting.text = voteuser
        Glide.with(mContext)
                .load(mMovieList[position].posterPath).placeholder(R.drawable.load)
                .into(holder.movieposter)

    }

    override fun getItemCount(): Int {
        return mMovieList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tittle: TextView
        internal var userratting: TextView
        internal var movieposter: ImageView

        init {
            tittle = itemView.findViewById(R.id.title_card) as TextView
            userratting = itemView.findViewById(R.id.user_rat_card) as TextView
            movieposter = itemView.findViewById(R.id.thumbnile_card) as ImageView
            itemView.setOnClickListener { v ->
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {

                    val MovieDetailCliced = mMovieList[pos]
                    val MovieIntent = Intent(mContext, DetailActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    MovieIntent.putExtra("id", mMovieList[pos].id)
                    MovieIntent.putExtra("original_title", mMovieList[pos].original_title)
                    MovieIntent.putExtra("poster_path", mMovieList[pos].posterPath)
                    MovieIntent.putExtra("overview", mMovieList[pos].overview)
                    MovieIntent.putExtra("vote_average", java.lang.Double.toString(mMovieList[pos].vote_average!!))
                    mContext.startActivity(MovieIntent)
                    Toast.makeText(v.context, " " + MovieDetailCliced.original_title, Toast.LENGTH_SHORT).show()
                }
            }

        }


    }

}
