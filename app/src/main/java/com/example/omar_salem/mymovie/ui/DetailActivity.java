package com.example.omar_salem.mymovie.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.omar_salem.mymovie.R;
import com.example.omar_salem.mymovie.data.FavoriteDbHelper;
import com.example.omar_salem.mymovie.model.Movie;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.github.ivbaranov.mfb.MaterialFavoriteButton.OnFavoriteChangeListener;



public class DetailActivity extends AppCompatActivity {
   private TextView MovieName,UserRating,Plotsynopsis;
   private ImageView MovieImgDetail;
   private Toolbar toolbar;
   private FavoriteDbHelper favoriteDbHelper;
   private Movie FavoriteMovie;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activety_detail);
        toolbar=(Toolbar)findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();
        MovieImgDetail=(ImageView)findViewById(R.id.thumbil_img);
        MovieName=(TextView)findViewById(R.id.title);
        Plotsynopsis=(TextView)findViewById(R.id.plotsynopsis);
        UserRating=(TextView)findViewById(R.id.user_rating);
        Intent DetailActivityStart=getIntent();
        if(DetailActivityStart.hasExtra("original_title"))
        {
            String thumbil =getIntent().getExtras().getString("poster_path");
            String moviename =getIntent().getExtras().getString("original_title");
            String ratting =getIntent().getExtras().getString("vote_average");
            String overview =getIntent().getExtras().getString("overview");
            Glide.with(this)
                    .load(thumbil)
                    .placeholder(R.drawable.load)
                    .into(MovieImgDetail);
            MovieName.setText(moviename);
            UserRating.setText(ratting);
            Plotsynopsis.setText(overview);

        }else {
            Toast.makeText(this,"somethings Wrong Details Can't start",Toast.LENGTH_SHORT).show();
        }
      MaterialFavoriteButton favoriteButton=(MaterialFavoriteButton)findViewById(R.id.favorite_btn_id) ;
      SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
      favoriteButton.setOnFavoriteChangeListener(new OnFavoriteChangeListener() {
        @Override
        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
          if(favorite)
          {
            SharedPreferences.Editor editor=getSharedPreferences("com.example.omar_salem.mymovie",MODE_PRIVATE).edit();
            editor.putBoolean("Add Movie",true);
            editor.commit();
            SaveMovies();
            Snackbar.make(buttonView,"Added To Favorite",Snackbar.LENGTH_SHORT).show();

          }else{
            int Id =getIntent().getExtras().getInt("id");
            favoriteDbHelper=new FavoriteDbHelper(DetailActivity.this);
            favoriteDbHelper.DeletFromFavorite(Id);
            SharedPreferences.Editor editor=getSharedPreferences("com.example.omar_salem.mymovie",MODE_PRIVATE).edit();
            editor.putBoolean("Remove Movie",true);
            editor.commit();
            Snackbar.make(buttonView,"Removed from Favorite",Snackbar.LENGTH_SHORT).show();
          }

        }
      });
    }
    private void initCollapsingToolbar()
    {
        final CollapsingToolbarLayout collapsingToolbarLayout
                =(CollapsingToolbarLayout) findViewById(R.id.Coll_bar);
        collapsingToolbarLayout.setTitle(" ");
        final AppBarLayout appBar=(AppBarLayout)findViewById(R.id.appbar);
        appBar.setExpanded(true);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow=false;
            int scroll=-1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if(scroll==-1)
             {
                 scroll=appBar.getTotalScrollRange();
             }
             if(scroll+verticalOffset==0)
             {
                 collapsingToolbarLayout.setTitle(getString(R.string.movie_detl));
                 isShow=true;

             }else if(isShow)
             {
                 collapsingToolbarLayout.setTitle(" ");
                 isShow=false;
             }
            }
        });
    }
    public void SaveMovies(){
      favoriteDbHelper= new FavoriteDbHelper(DetailActivity.this);
      FavoriteMovie =  new Movie();
      int IdSaved=getIntent().getExtras().getInt("id");
      String Savedthumbil =getIntent().getExtras().getString("poster_path");
     FavoriteMovie.setId(IdSaved);
     FavoriteMovie.setOriginal_title(MovieName.getText().toString().trim());
     FavoriteMovie.setOverview(Plotsynopsis.getText().toString().trim());
     FavoriteMovie.setPosterPath(Savedthumbil);
     FavoriteMovie.setVote_average(Double.valueOf(UserRating.getText().toString()));
      favoriteDbHelper.AddToFavorite(FavoriteMovie);
    }
}
