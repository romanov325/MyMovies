package com.example.mymovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// Интерфейсы доступа к базе данных
@Dao
public interface MovieDao {

    // Получить объект LiveData со всеми фильмами
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    // Получить объект LiveData со всеми любимыми фильмами
    @Query("SELECT * FROM favourite_movies")
    LiveData<List<FavouriteMovie>> getAllFavouriteMovies();

    // Получить объект Movie по id
    @Query("SELECT * FROM movies where id == :movieId")
    Movie getMovieById(int movieId);

    // Получить объект FavouriteMovie по id
    @Query("SELECT * FROM favourite_movies where id == :movieId")
    FavouriteMovie getFavouriteMovieById(int movieId);

    // Очистить таблицу "movies"
    @Query("DELETE FROM movies")
    void deleteAllMovies();

    // Вставить объект Movie в таблицу "movies"
    @Insert
    void insertMovie(Movie movie);

    // Вставить объект FavouriteMovie в таблицу "favourite_movies"
    @Insert
    void insertFavouriteMovie(FavouriteMovie favouriteMovie);

    // Удалить объект Movie из таблицы "movies"
    @Delete
    void deleteMovie(Movie movie);

    // Удалить объект FavouriteMovie из таблицы "favourite_movies"
    @Delete
    void deleteFavouriteMovie(FavouriteMovie favouriteMovie);
}
