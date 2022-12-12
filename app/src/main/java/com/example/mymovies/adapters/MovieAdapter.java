package com.example.mymovies.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.MainActivity;
import com.example.mymovies.R;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    // Объект массива фильмов в адаптере
    private List<Movie> movieAdapterArrayList;

    // Конструктор для создания массива с фильмами в адаптере
    public MovieAdapter() {
        movieAdapterArrayList = new ArrayList<>();
    }

    // Вернуть из адаптера массив фильмов
    public List<Movie> getMovieAdapterArrayList() {
        return movieAdapterArrayList;
    }

    // Добавить весь массив фильмов в массив адаптера
    public void addMoviesToAdapterArrayList(List<Movie> movieArrayList) {
        this.movieAdapterArrayList.addAll(movieArrayList);
        notifyDataSetChanged();
    }

    // Сеттер для установки массива фильмов в адаптере
    public void setMovieAdapterArrayList(List<Movie> movieArrayList) {
        this.movieAdapterArrayList = movieArrayList;
        notifyDataSetChanged();
    }

    // Очистить массив фильмов в адаптере
    public void clearAdapter()  {
        this.movieAdapterArrayList.clear();
        notifyDataSetChanged();
    }

    // Объект интерфейса щелчков на постер
    private OnPosterClickListener onPosterClickListener;

    // Объект интерфейса подгрузки новых данных
    private OnReachEndListener onReachEndListener;

    // Интерфейс слушателя щелчков на постер
    public interface OnPosterClickListener{
        void onPosterClickPosition(int position);
    }

    // Интерфейс слушателя подгрузки новых данных
    public interface OnReachEndListener {
        void onReachEnd();
    }

    // Сеттер интерфейса подгрузки новых данных
    public void setOnReachEndListener(OnReachEndListener onReachEndListener) {
        this.onReachEndListener = onReachEndListener;
    }

    // Сеттер интерфейса щелчков на постер
    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    // Создать ViewHolder с макетом movie_item.xml для постера
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        // При пролистывании постеров заранее вызвать интерфейс подгрузки новых данных
        if(movieAdapterArrayList.size() >= 20 && position > movieAdapterArrayList.size() - 4 && onReachEndListener != null)  {
            onReachEndListener.onReachEnd();
        }
        // Загрузить постеры к фильмам из ячеек массива в позиции ViewHolder равные индексам ячеек массива
        Movie movie = movieAdapterArrayList.get(position);
        Picasso.get().load(movie.getPosterPath()).placeholder(R.drawable.clapperboard).into(holder.imageViewSmallPoster);
        Log.i("tag", movie.getPosterPath());
    }

    // Получить кол-во фильмов в массиве адаптера
    @Override
    public int getItemCount() {
        return movieAdapterArrayList.size();
    }

    // ViewHolder(держатель видимых частей) для списка фильмов
    class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewSmallPoster;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            // Присвоить значение для View с постером
            imageViewSmallPoster = itemView.findViewById(R.id.imageViewSmallPoster);
            //Установить слушатель щелчков на постер
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onPosterClickListener != null)  {
                        //Передать в интерфейс позицию нажатого постера
                        onPosterClickListener.onPosterClickPosition(getAdapterPosition());
                    }
                }
            });
        }
    }
}
