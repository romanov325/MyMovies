package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.adapters.ReviewAdapter;
import com.example.mymovies.adapters.TrailerAdapter;
import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.example.mymovies.data.Review;
import com.example.mymovies.data.Trailer;
import com.example.mymovies.utils.JSONUtils;
import com.example.mymovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOriginalTitle;
    private TextView textViewRating;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private ScrollView scrollViewMovieInfo;
    private FavouriteMovie favouriteMovie;
    private ImageView imageViewAddToFavourite;

    private int id;
    private Movie detailsOfMovie;

    private MainViewModel mainViewModel;

    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private static String lang;

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
        setContentView(R.layout.activity_detail);

        //Получить язык устройства
        lang = Locale.getDefault().getLanguage();

        imageViewBigPoster = findViewById(R.id.imageViewBigPoster);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewRating = findViewById(R.id.textViewRating);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewOverview = findViewById(R.id.textViewOverview);
        imageViewAddToFavourite = findViewById(R.id.imageViewAddToFavourite);

        //Получить id фильма через интент
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("id")) {
            id = intent.getIntExtra("id", -1);
        } else {
            //Иначе закрыть активность
            finish();
        }

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        /* Если фильм есть в таблице "favourite_movies" установить желтую звездочку  и присвоить
        значение для объекта FavouriteMovie*/
        setFavouriteMovie();
        
        // Присвоить полное описанием фильма из таблицы "movies", иначе из таблицы "favourite_movies"
        detailsOfMovie = mainViewModel.getMovieById(id);
        if(detailsOfMovie == null)  {
            detailsOfMovie = mainViewModel.getFavouriteMovieById(id);
        }

        //Установить описание фильма в макете
        if (detailsOfMovie != null) {
            //Вставить дефолтную картинку пока постер не загружен
            Picasso.get().load(detailsOfMovie.getBigPosterPath()).placeholder(R.drawable.clapperboard).into(imageViewBigPoster);
            textViewTitle.setText(detailsOfMovie.getTitle());
            textViewOriginalTitle.setText(detailsOfMovie.getOriginalTitle());
            textViewRating.setText(Double.toString(detailsOfMovie.getVoteAverage()));
            textViewReleaseDate.setText(detailsOfMovie.getReleaseDate());
            textViewOverview.setText(detailsOfMovie.getOverview());
        }

        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        trailerAdapter = new TrailerAdapter();

        //Установить интерфейс у адаптера и запустить трейлер
        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onTrailerClick(String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        reviewAdapter = new ReviewAdapter();

        //Установить отображение строками
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        //Установить адаптеры
        recyclerViewReviews.setAdapter(reviewAdapter);
        recyclerViewTrailers.setAdapter(trailerAdapter);

        //Получить массив трейлеров для фильма из JSON и установить в адаптере
        ArrayList<Trailer> trailersArrayList = JSONUtils.getTrailersFromJSON(NetworkUtils.getJSONTrailerFromNetwork(detailsOfMovie.getId(), lang));
        trailerAdapter.setTrailerAdapterArrayList(trailersArrayList);
        //Получить массив отзывов для фильма из JSON и установить в адаптере
        ArrayList<Review> reviewsArrayList = JSONUtils.getReviewsFromJSON(NetworkUtils.getJSONReviewFromNetwork(detailsOfMovie.getId(), lang));
        reviewAdapter.setReviewAdapterArrayList(reviewsArrayList);

        //Перематываем ScrollView на первую строчку
        scrollViewMovieInfo = findViewById(R.id.scrollViewMovieInfo);
        scrollViewMovieInfo.smoothScrollTo(0, 0);
    }

    // При нажатии на звездочку вставить FavouriteMovie в таблицу, иначе удалить
    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            mainViewModel.insertFavouriteMovie(new FavouriteMovie(detailsOfMovie));
        } else {
            mainViewModel.deleteFavouriteMovie(favouriteMovie);
        }
        setFavouriteMovie();
    }

    // Установить желтую звездочку если фильм есть в таблице "favourite_movies"
    private void setFavouriteMovie()    {
        favouriteMovie = mainViewModel.getFavouriteMovieById(id);
        if(favouriteMovie == null)  {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_grey_star);
        }
        else {
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_yellow_star);
        }
    }
}
