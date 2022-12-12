package com.example.mymovies.utils;

import com.example.mymovies.data.Movie;
import com.example.mymovies.data.Review;
import com.example.mymovies.data.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    //Общий ключ
    private static final String KEY_RESULTS = "results";

    //Ключи для отзывов
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";

    //Ключи для трейлеров
    private static final String KEY_KEY_OF_TRAILER = "key";
    private static final String KEY_NAME = "name";
    private static final String BASE_YOUTUBE_URL = "https://www.youtube.com/watch?v=";

    //Ключи для фильмов
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_ID = "id";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW = "overview";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_TITLE = "title";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_VOTE_COUNT = "vote_count";

    //Ключи для постеров
    public static final String BASE_POSTER_URL = "https://image.tmdb.org/t/p/";
    public static final String BIG_POSTER_SIZE = "w780";
    public static final String SMALL_POSTER_SIZE = "w185";

    //Вернуть ArrayList с фильмами из JSONObject
    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObjectFromNetwork) {
        ArrayList<Movie> result = new ArrayList<>();
        if(jsonObjectFromNetwork == null)  {
            return result;
        }
        try {
            JSONArray jsonArrayMovies = jsonObjectFromNetwork.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArrayMovies.length(); i++)    {
                JSONObject objectMovie = jsonArrayMovies.getJSONObject(i);
                String backdropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                int id = objectMovie.getInt(KEY_ID);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = BASE_POSTER_URL + SMALL_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL + BIG_POSTER_SIZE + objectMovie.getString(KEY_POSTER_PATH);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                String title = objectMovie.getString(KEY_TITLE);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                Movie movie = new Movie(backdropPath, id, originalTitle, overview, posterPath, bigPosterPath, releaseDate, title, voteAverage, voteCount);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Вернуть ArrayList с трейлерами из JSONObject
    public static ArrayList<Trailer> getTrailersFromJSON(JSONObject jsonTrailerFromNetwork) {
        ArrayList<Trailer> result = new ArrayList<>();
        if(jsonTrailerFromNetwork == null)  {
            return result;
        }
        try {
            JSONArray jsonArrayTrailers = jsonTrailerFromNetwork.getJSONArray(KEY_RESULTS);
            for (int i = 0; i < jsonArrayTrailers.length(); i++)    {
                JSONObject objectTrailer = jsonArrayTrailers.getJSONObject(i);
                String keyOfTrailer = BASE_YOUTUBE_URL + objectTrailer.getString(KEY_KEY_OF_TRAILER);
                String name = objectTrailer.getString(KEY_NAME);
                Trailer trailer = new Trailer(keyOfTrailer, name);
                result.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Вернуть ArrayList с отзывами из JSONObject
    public static ArrayList<Review> getReviewsFromJSON(JSONObject jsonReviewFromNetwork)    {
        ArrayList<Review> result = new ArrayList<>();
        if(jsonReviewFromNetwork == null)   {
            return result;
        }
        try {
            JSONArray jsonArrayReviews = jsonReviewFromNetwork.getJSONArray(KEY_RESULTS);
            for(int i = 0; i < jsonArrayReviews.length(); i++)  {
                JSONObject objectReview = jsonArrayReviews.getJSONObject(i);
                String author = objectReview.getString(KEY_AUTHOR);
                String content = objectReview.getString(KEY_CONTENT);
                Review review = new Review(author, content);
                result.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
