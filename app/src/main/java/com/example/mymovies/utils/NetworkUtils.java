package com.example.mymovies.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    //Базовые URL
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String BASE_URL_TRAILER = "https://api.themoviedb.org/3/movie/%s/videos";
    private static final String BASE_URL_REVIEW = "https://api.themoviedb.org/3/movie/%s/reviews";

    //Ключи для списка фильмов
    private static final String PARAMS_API_KEY = "api_key";
    private static final String PARAMS_LANGUAGE = "language";
    private static final String PARAMS_SORT_BY = "sort_by";
    private static final String PARAMS_PAGE = "page";
    private static final String PARAMS_MIN_VOTE_COUNT = "vote_count.gte";

    //Значения ключей для списка фильмов
    private static final String API_KEY = "67f47d341ce8bd4f1ca5771f1d5456e6";
    private static final String SORT_BY_POPULARITY = "popularity.desc";
    private static final String SORT_BY_TOP_RATED = "vote_average.desc";
    private static final String MIN_VOTE_COUNT = "1000";

    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;

    //Вернуть JSONObject со списком фильмов по методу сортировки и номеру страницы
    public static JSONObject getJSONFromNetwork(int sortBy, int page, String lang)    {
        URL url = buildURL(sortBy,page, lang);
        JSONObject result = null;
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Вернуть JSONObject со списком трейлеров по id фильма
    public static JSONObject getJSONTrailerFromNetwork(int movieId, String lang)    {
        JSONObject result = null;
        URL url = buildURLTrailer(movieId, lang);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Вернуть JSONObject со списком отзывов по id фильма
    public static JSONObject getJSONReviewFromNetwork(int movieId, String lang)    {
        JSONObject result = null;
        URL url = buildURLReview(movieId, lang);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Вернуть URL со списком фильмов по методу сортировки и номеру страницы
    public static URL buildURL(int sortBy, int page, String lang) {
        URL result = null;
        String methodOfSort;
        if (sortBy == POPULARITY) {
            methodOfSort = SORT_BY_POPULARITY;
        } else {
            methodOfSort = SORT_BY_TOP_RATED;
        }
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, lang)
                .appendQueryParameter(PARAMS_SORT_BY, methodOfSort)
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))
                .appendQueryParameter(PARAMS_MIN_VOTE_COUNT, MIN_VOTE_COUNT)
                .build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Вернуть URL со списком трейлеров по id фильма
    public static URL buildURLTrailer(int movieId, String lang) {
        Uri uri = Uri.parse(String.format(BASE_URL_TRAILER, movieId)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, lang)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Вернуть URL со списком отзывов по id фильма
    public static URL buildURLReview(int movieId, String lang) {
        Uri uri = Uri.parse(String.format(BASE_URL_REVIEW, movieId)).buildUpon()
                .appendQueryParameter(PARAMS_API_KEY, API_KEY)
                .appendQueryParameter(PARAMS_LANGUAGE, lang)
                .build();
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Добавить Bundle в конструктор загрузчика и установить слушатель начала загрузки
    public static class JSONLoader extends AsyncTaskLoader<JSONObject> {

        private Bundle bundle;
        private OnStartLoadingListener onStartLoadingListener;

        // Интерфейс начала загрузки
        public interface OnStartLoadingListener{
            void onStartLoading();
        }

        // Сеттер интерфейса начала загрузки
        public void setOnStartLoadingListener(OnStartLoadingListener onStartLoadingListener) {
            this.onStartLoadingListener = onStartLoadingListener;
        }

        // Конструктор для Bundle
        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            // Установить интерфейс начала загрузки
            if(onStartLoadingListener != null)  {
                onStartLoadingListener.onStartLoading();
            }
            // Начать новую загрузку
            forceLoad();
        }

        //Вернуть JSONObject из Bundle
        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if(bundle == null)  {
                return null;
            }
            String urlAsString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject result = null;
            if(url == null)    {
                return result;
            }
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null)    {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                result = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(httpURLConnection != null)   {
                    httpURLConnection.disconnect();
                }
            }
            return result;
        }
    }

    // Вернуть JSONObject из URL
    private static class JSONLoadTask extends AsyncTask<URL,Void, JSONObject>   {

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if(urls == null || urls.length == 0)    {
                return result;
            }
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null)    {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                result = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(httpURLConnection != null)   {
                    httpURLConnection.disconnect();
                }
            }
            return result;
        }
    }
}
