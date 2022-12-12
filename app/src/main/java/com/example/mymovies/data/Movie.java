package com.example.mymovies.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// Ключи для таблицы "movies"
@Entity(tableName = "movies")
public class Movie {
    private String backdropPath;
    // Уникальный авто-ключ
    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int id;
    private String originalTitle;
    private String overview;
    private String posterPath;
    private String bigPosterPath;
    private String releaseDate;
    private String title;
    private double voteAverage;
    private int voteCount;

    // Конструктор для создания объекта Movie для таблицы "movies"
    public Movie(String backdropPath, int uniqueId, int id, String originalTitle, String overview, String posterPath, String bigPosterPath, String releaseDate, String title, double voteAverage, int voteCount) {
        this.backdropPath = backdropPath;
        this.uniqueId = uniqueId;
        this.id = id;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    // Конструктор для создания объекта Movie, но не для базы данных
    @Ignore
    public Movie(String backdropPath, int id, String originalTitle, String overview, String posterPath, String bigPosterPath, String releaseDate, String title, double voteAverage, int voteCount) {
        this.backdropPath = backdropPath;
        this.id = id;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.releaseDate = releaseDate;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    // Геттеры для получения значений объекта Movie
    public String getBackdropPath() {
        return backdropPath;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBigPosterPath() {
        return bigPosterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    // Сеттеры для установки значений объекта Movie
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setBigPosterPath(String bigPosterPath) {
        this.bigPosterPath = bigPosterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}


