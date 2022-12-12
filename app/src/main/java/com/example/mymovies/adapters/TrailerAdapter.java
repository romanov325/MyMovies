package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.data.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    // Объект массива трейлеров в адаптере
    private ArrayList<Trailer> trailerAdapterArrayList;

    // Объект интерфейса щелчков на трейлер
    private OnTrailerClickListener onTrailerClickListener;

    // Создать ViewHolder с макетом trailer_item.xml для трейлера
    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    // Загрузить трейлеры к фильмам из ячеек массива в позиции ViewHolder равные индексам ячеек массива
    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer trailer = trailerAdapterArrayList.get(position);
        holder.textViewTrailerName.setText(trailer.getName());
    }

    // Получить кол-во трейлеров в массиве адаптера
    @Override
    public int getItemCount() {
        return trailerAdapterArrayList.size();
    }

    // Сеттер для установки массива трейлеров в адаптере
    public void setTrailerAdapterArrayList(ArrayList<Trailer> trailerAdapterArrayList) {
        this.trailerAdapterArrayList = trailerAdapterArrayList;
        notifyDataSetChanged();
    }

    // Интерфейс слушателя щелчков на трейлер
    public interface OnTrailerClickListener{
        void onTrailerClick(String url);
    }

    // Сеттер интерфейса щелчков на трейлер
    public void setOnTrailerClickListener(OnTrailerClickListener onTrailerClickListener) {
        this.onTrailerClickListener = onTrailerClickListener;
    }

    // ViewHolder(держатель видимых частей) для трейлеров
    class TrailerViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTrailerName;

        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            // Присвоить значение для View с трейлером
            textViewTrailerName = itemView.findViewById(R.id.textViewTrailerName);
            //Установить слушатель щелчков на трейлер
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Передать URL нажатого трейлера в интерфейс
                    if(onTrailerClickListener != null)  {
                        onTrailerClickListener.onTrailerClick(trailerAdapterArrayList.get(getAdapterPosition()).getKey());
                    }
                }
            });
        }
    }
}
