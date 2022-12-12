package com.example.mymovies.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

//Класс AndroidViewModel
public class MainViewModel extends AndroidViewModel {

    //Объект базы данных
    private static MovieDatabase database;

    //Объекты LiveData
    private LiveData<List<Movie>> liveDataMovies;
    private LiveData<List<FavouriteMovie>> liveDataFavouriteMovies;

    public MainViewModel(@NonNull Application application) {
        super(application);

        // Присвоить значение базе данных
        database = MovieDatabase.getInstance(getApplication());

        // Загрузить в LiveData все объекты Movies и FavouriteMovies из базы данных
        liveDataMovies = database.movieDao().getAllMovies();
        liveDataFavouriteMovies = database.movieDao().getAllFavouriteMovies();
    }

    // Вернуть объект Movie из базы данных по id
    public Movie getMovieById(int id) {
        try {
            return new getMovieByIdTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Вернуть объект FavouriteMovie из базы данных по id
    public FavouriteMovie getFavouriteMovieById(int id) {
        try {
            return new getFavouriteMovieByIdTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Вернуть массив фильмов из LiveData
    public LiveData<List<Movie>> getLiveDataMovies() {
        return liveDataMovies;
    }

    // Вернуть массив любимых фильмов из LiveData
    public LiveData<List<FavouriteMovie>> getLiveDataFavouriteMovies() {
        return liveDataFavouriteMovies;
    }

    // Очистить таблицу "movies"
    public void deleteAllMovies() {
        new deleteAllMoviesTask().execute();
    }

    // Вставить объект Movie в таблицу "movies"
    public void insertMovie(Movie movie) {
        new insertMovieTask().execute(movie);
    }

    // Вставить объект FavouriteMovie в таблицу "favourite_movies"
    public void insertFavouriteMovie(FavouriteMovie favouriteMovie) {
        new insertFavouriteMovieTask().execute(favouriteMovie);
    }

    // Удалить объект Movie из таблицы "movies"
    public void deleteMovie(Movie movie)    {
        new deleteMovieTask().execute(movie);
    }

    // Удалить объект FavouriteMovie из таблицы "favourite_movies"
    public void deleteFavouriteMovie(FavouriteMovie favouriteMovie) {
        new deleteFavouriteMovieTask().execute(favouriteMovie);
    }

    private static class getMovieByIdTask extends AsyncTask<Integer, Void, Movie> {

        @Override
        protected Movie doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class getFavouriteMovieByIdTask extends AsyncTask<Integer, Void, FavouriteMovie> {

        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if(integers != null && integers.length > 0) {
                return database.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }

    private static class deleteAllMoviesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.movieDao().deleteAllMovies();
            return null;
        }
    }

    private static class insertMovieTask extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies != null && movies.length > 0) {
                database.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }

    private static class insertFavouriteMovieTask extends AsyncTask<FavouriteMovie, Void, Void> {

        @Override
        protected Void doInBackground(FavouriteMovie... favouriteMovies) {
            if(favouriteMovies != null && favouriteMovies.length > 0)   {
                database.movieDao().insertFavouriteMovie(favouriteMovies[0]);
            }
            return null;
        }
    }
    private static class deleteMovieTask extends AsyncTask<Movie, Void, Void>   {

        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies != null && movies.length > 0) {
                database.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }

    private static class deleteFavouriteMovieTask extends  AsyncTask<FavouriteMovie, Void, Void>    {

        @Override
        protected Void doInBackground(FavouriteMovie... favouriteMovies) {
            if(favouriteMovies != null && favouriteMovies.length > 0) {
                database.movieDao().deleteFavouriteMovie(favouriteMovies[0]);
            }
            return null;
        }
    }
}
