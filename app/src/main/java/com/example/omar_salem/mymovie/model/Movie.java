package com.example.omar_salem.mymovie.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omar_Salem on 9/7/2017.
 */

public class Movie {

    String baseImgUrl="https://image.tmdb.org/t/p/w500";
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("id")
    private Integer id;
    @SerializedName("title")
    private String title;
    @SerializedName("video")
    private boolean video;
    @SerializedName("vote_average")
    private Double vote_average;
     @SerializedName("popularity")
    private Double popularity;
    @SerializedName("original_language")
    private String original_language;
    @SerializedName("genres")
    private List<Integer> genres=new ArrayList<Integer>();
    @SerializedName("vote_count")
    private Integer vote_count;


    public Movie(String posterPath, boolean adult, String overview, String original_title, Integer id, String title, boolean video, Double vote_average, Double popularity, String original_language, List<Integer> genres, Integer vote_count) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.original_title = original_title;
        this.id = id;
        this.title = title;
        this.video = video;
        this.vote_average = vote_average;
        this.popularity = popularity;
        this.original_language = original_language;
        this.genres = genres;
        this.vote_count = vote_count;
    }

    public Movie() {

    }

    public String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500"+ posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }
}
