package com.example.mymovies.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Создать базу данных с указанием входных таблиц и версии базы
@Database(entities = {Movie.class, FavouriteMovie.class},  version = 5, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    // Присвоить название для базы данных
    private static final String DB_NAME = "movies.db";

    /* Использовать паттерн Singleton и блок синхронизации, чтобы не было возможности создать
    несколько объектов базы данных и чтобы не было возможности создавать 2 базы одновременно из
    нескольких потоков */
    private static MovieDatabase database;
    private static final Object LOCK = new Object();

    public static MovieDatabase getInstance(Context context)    {
        synchronized (LOCK) {
            if(database == null)    {
                database = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return database;
    }

    // Создать абстрактный класс для использования интерфейсов MovieDao в базе данных
    public abstract MovieDao movieDao();
}
