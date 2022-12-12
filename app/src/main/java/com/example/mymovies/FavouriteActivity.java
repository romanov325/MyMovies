package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.mymovies.adapters.MovieAdapter;
import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFavouritePosters;
    private MovieAdapter movieAdapter;
    private MainViewModel mainViewModel;

    // При создании Menu передать макет main_menu.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Получить id нажатого элемента Menu и запустить нужную активность
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menuItemToMain:
                Intent intentToMain = new Intent(this, MainActivity.class);
                startActivity(intentToMain);
                break;
            case R.id.menuItemToFavourite:
                Intent intentToFavourite = new Intent(this, FavouriteActivity.class);
                startActivity(intentToFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        recyclerViewFavouritePosters = findViewById(R.id.recyclerViewFavouritePosters);
        // Установить отображение постеров сеткой в 3 ряда
        recyclerViewFavouritePosters.setLayoutManager(new GridLayoutManager(this, 3));
        movieAdapter = new MovieAdapter();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // Установить адаптер для RecyclerView
        recyclerViewFavouritePosters.setAdapter(movieAdapter);
        //Создать observer для LiveData
        LiveData<List<FavouriteMovie>> favouriteMovieFromLiveData = mainViewModel.getLiveDataFavouriteMovies();
        favouriteMovieFromLiveData.observe(this, new Observer<List<FavouriteMovie>>() {

            // При изменении базы данных, установить новый массив фильмов в адаптере
            @Override
            public void onChanged(List<FavouriteMovie> favouriteMovies) {
                /* Установить дочерний массив <List<FavouriteMovies> в адаптер нельзя. Привести
                к родительскому типу List<Movie> */
                List<Movie> favouriteMovieList = new ArrayList<>();
                if(favouriteMovieList != null)  {
                    favouriteMovieList.addAll(favouriteMovies);
                    movieAdapter.setMovieAdapterArrayList(favouriteMovieList);
                }
            }
        });

        //Установить интерфейс у адаптера и получить позицию нажатого постера
        movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClickPosition(int position) {
                //Получить id фильма по позиции нажатого постера
                Movie movie = movieAdapter.getMovieAdapterArrayList().get(position);
                // Запустить DetailActivity и передать id фильма
                Intent intent = new Intent(FavouriteActivity.this, DetailActivity.class);
                intent.putExtra("id", movie.getId());
                startActivity(intent);
            }
        });
    }
}