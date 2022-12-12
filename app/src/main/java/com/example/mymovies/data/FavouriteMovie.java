package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;

// Ключи для таблицы "favourite_movies" наследуются от Movie
@Entity(tableName = "favourite_movies")
public class FavouriteMovie extends Movie {
    public FavouriteMovie(String backdropPath, int uniqueId, int id, String originalTitle, String overview, String posterPath, String bigPosterPath, String releaseDate, String title, double voteAverage, int voteCount) {
        super(backdropPath, uniqueId, id, originalTitle, overview, posterPath, bigPosterPath, releaseDate, title, voteAverage, voteCount);
    }

    // Конструктор для создания объекта FavouriteMovie, но не для базы данных
    @Ignore
    public FavouriteMovie(Movie movie)  {
        super(movie.getBackdropPath(), movie.getUniqueId(), movie.getId(), movie.getOriginalTitle(), movie.getOverview(), movie.getPosterPath(), movie.getBigPosterPath(), movie.getReleaseDate(), movie.getTitle(), movie.getVoteAverage(), movie.getVoteCount());
    }
}
