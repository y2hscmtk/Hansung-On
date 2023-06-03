package com.example.myapplication;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

//영화 데이터를 저장하기 위한 클래스
public class Movie implements Serializable {
    private String overview;

    private String original_language;

    private String original_title;

    private String imdb_id;

    private Long runtime;

    private Boolean video;

    private String title;

    private String poster_path;

    private String spoken_languages;

    private Long revenue;

    private String production_companies;

    private String release_date;

    private String genres;

    private ArrayList<String> genreList;

    private BigDecimal popularity;

    private BigDecimal vote_average;

    private String belongs_to_collection;

    private String production_countries;

    private String tagline;

    private Long id;

    private Boolean adult;

    private Long vote_count;

    private Long budget;

    private String homepage;

    private String status;

    //"review"
    @SerializedName("review")
    private String review;
    private ArrayList<String> reviewList;

    //"crew"
    //@SerializedName("crew")
    private String crew;
    private ArrayList<String> crewList;
    //"cast"
    //@SerializedName("cast")
    private String cast;
    private ArrayList<String> castList;


    private String similar_movies;

    public ArrayList<String> getSimilarMovieList() {
        return similarMovieList;
    }

    public void setSimilarMovieList() {
        this.similarMovieList = new ArrayList<String>();
    }

    private ArrayList<String> similarMovieList;


    public Movie(String overview, String original_language, String original_title, String imdb_id, Long runtime, Boolean video, String title, String poster_path, String spoken_languages, Long revenue, String production_companies, String release_date, String genres, BigDecimal popularity, BigDecimal vote_average, String belongs_to_collection, String production_countries, String tagline, Long id, Boolean adult, Long vote_count, Long budget, String homepage, String status, String review, String crew, String cast, String similar_movies) {
        this.overview = overview;
        this.original_language = original_language;
        this.original_title = original_title;
        this.imdb_id = imdb_id;
        this.runtime = runtime;
        this.video = video;
        this.title = title;
        this.poster_path = poster_path;
        this.spoken_languages = spoken_languages;
        this.revenue = revenue;
        this.production_companies = production_companies;
        this.release_date = release_date;
        this.genres = genres;
        this.popularity = popularity;
        this.vote_average = vote_average;
        this.belongs_to_collection = belongs_to_collection;
        this.production_countries = production_countries;
        this.tagline = tagline;
        this.id = id;
        this.adult = adult;
        this.vote_count = vote_count;
        this.budget = budget;
        this.homepage = homepage;
        this.status = status;
        this.review = review;
        this.cast = cast;
        this.crew = crew;
        this.similar_movies = similar_movies;
    }


/*
* //"crew"
    private String crew;
    private ArrayList<String> crewList;
    //"cast"
    private String cast;
    private ArrayList<String> castList;
* */

    public String getSimilar_movies() {
        return similar_movies;
    }

    public void setSimilar_movies(String similar_movies) {
        this.similar_movies = similar_movies;
    }

    public void setCrewList(){this.crewList=new ArrayList<>();}
    public void setCastList(){this.castList=new ArrayList<>();}

    public ArrayList<String> getCrewList(){return this.crewList;}
    public ArrayList<String> getCastList(){return this.castList;}


    public void setCrew(String crew){this.crew=crew;}
    public void setCast(String cast){this.cast=cast;}

    public String getCrew(){return this.crew;}
    public String getCast(){return this.cast;}


    public void setReviewList(){this.reviewList = new ArrayList<String>();}
    public ArrayList<String> getReviewList(){return this.reviewList;}

    public void setGenreList() {this.genreList = new ArrayList<>();}

    public ArrayList<String> getGenreList() {return this.genreList;}

    public String getOverview() {
        return this.overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginal_language() {
        return this.original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }


    public String getReview(){return this.review;}

    public void setReview(String review){this.review = review;}

    public String getOriginal_title() {
        return this.original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getImdb_id() {
        return this.imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public Long getRuntime() {
        return this.runtime;
    }

    public void setRuntime(Long runtime) {
        this.runtime = runtime;
    }

    public Boolean getVideo() {
        return this.video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return this.poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getSpoken_languages() {
        return this.spoken_languages;
    }

    public void setSpoken_languages(String spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public Long getRevenue() {
        return this.revenue;
    }

    public void setRevenue(Long revenue) {
        this.revenue = revenue;
    }

    public String getProduction_companies() {
        return this.production_companies;
    }

    public void setProduction_companies(String production_companies) {
        this.production_companies = production_companies;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getGenres() {
        return this.genres;
    }


    public void setGenres(String genres) {this.genres = genres;}

    public BigDecimal getPopularity() {
        return this.popularity;
    }

    public void setPopularity(BigDecimal popularity) {
        this.popularity = popularity;
    }

    public BigDecimal getVote_average() {
        return this.vote_average;
    }

    public void setVote_average(BigDecimal vote_average) {
        this.vote_average = vote_average;
    }

    public String getBelongs_to_collection() {
        return this.belongs_to_collection;
    }

    public void setBelongs_to_collection(String belongs_to_collection) {
        this.belongs_to_collection = belongs_to_collection;
    }

    public String getProduction_countries() {
        return this.production_countries;
    }

    public void setProduction_countries(String production_countries) {
        this.production_countries = production_countries;
    }

    public String getTagline() {
        return this.tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return this.adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public Long getVote_count() {
        return this.vote_count;
    }

    public void setVote_count(Long vote_count) {
        this.vote_count = vote_count;
    }

    public Long getBudget() {
        return this.budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
