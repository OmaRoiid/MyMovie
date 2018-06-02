package com.example.omar_salem.mymovie.model

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

/**
 * Created by Omar_Salem on 9/7/2017.
 */

class Movie {

    internal var baseImgUrl = "https://image.tmdb.org/t/p/w500"
    @SerializedName("poster_path")
    var posterPath: String? = null
        get() = "https://image.tmdb.org/t/p/w500" + field!!
    @SerializedName("adult")
    var isAdult: Boolean = false
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("original_title")
    var original_title: String? = null
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("title")
    var title: String? = null
    @SerializedName("video")
    var isVideo: Boolean = false
    @SerializedName("vote_average")
    var vote_average: Double? = null
    @SerializedName("popularity")
    var popularity: Double? = null
    @SerializedName("original_language")
    var original_language: String? = null
    @SerializedName("genres")
    var genres: List<Int> = ArrayList()
    @SerializedName("vote_count")
    var vote_count: Int? = null


    constructor(posterPath: String, adult: Boolean, overview: String, original_title: String, id: Int?, title: String, video: Boolean, vote_average: Double?, popularity: Double?, original_language: String, genres: List<Int>, vote_count: Int?) {
        this.posterPath = posterPath
        this.isAdult = adult
        this.overview = overview
        this.original_title = original_title
        this.id = id
        this.title = title
        this.isVideo = video
        this.vote_average = vote_average
        this.popularity = popularity
        this.original_language = original_language
        this.genres = genres
        this.vote_count = vote_count
    }

    constructor() {

    }
}
