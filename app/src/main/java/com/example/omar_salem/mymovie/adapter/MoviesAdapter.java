package com.example.omar_salem.mymovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.omar_salem.mymovie.DetailActivity;
import com.example.omar_salem.mymovie.R;
import com.example.omar_salem.mymovie.model.Movie;

import java.util.List;

/**
 * Created by Omar_Salem on 9/7/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;

    public MoviesAdapter (Context context,List<Movie> MovieList)
    {
        this.mContext=context;
        this.mMovieList=MovieList;

    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mV= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_card,parent,false);
        return new MyViewHolder(mV);
    }

    @Override
    public void onBindViewHolder(final MoviesAdapter.MyViewHolder holder, int position) {
        holder.tittle.setText(mMovieList.get(position).getOriginal_title());
        String voteuser=Double.toString(mMovieList.get(position).getVote_average());
        holder.userratting.setText(voteuser);
        Glide.with(mContext)
                .load(mMovieList.get(position).getPosterPath()).placeholder(R.drawable.load)//that when  img down  that  load  img  show  until it  down
                .into(holder.movieposter);

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tittle,userratting;
        ImageView movieposter;

        public MyViewHolder(View itemView) {
           super(itemView);
            tittle=(TextView)itemView.findViewById(R.id.title_card);
            userratting=(TextView)itemView.findViewById(R.id.user_rat_card);
             movieposter=(ImageView)itemView.findViewById(R.id.thumbnile_card) ;
itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        int pos=getAdapterPosition();
        if(pos != RecyclerView.NO_POSITION)
        {

            Movie MovieDetailCliced= mMovieList.get(pos);
            Intent MovieIntent = new Intent(mContext, DetailActivity.class);
            MovieIntent.putExtra("original_title",mMovieList.get(pos).getOriginal_title());
            MovieIntent.putExtra("poster_path",mMovieList.get(pos).getPosterPath());
            MovieIntent.putExtra("overview",mMovieList.get(pos).getOverview());
            MovieIntent.putExtra("vote_average",Double.toString(mMovieList.get(pos).getVote_average()));
            MovieIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(MovieIntent);
            Toast.makeText(v.getContext(),"Cliced on"+ MovieDetailCliced.getOriginal_title(),Toast.LENGTH_SHORT).show();
        }
    }
});

        }


    }

}
