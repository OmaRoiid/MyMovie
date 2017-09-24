package com.example.omar_salem.mymovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by Omar_Salem on 9/7/2017.
 */

public class DetailActivity extends AppCompatActivity {
   private TextView MovieName,UserRating,Plotsynopsis;
   private ImageView MovieImgDetail;
   private Toolbar toolbar;

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
}
