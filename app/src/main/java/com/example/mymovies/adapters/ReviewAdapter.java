package com.example.mymovies.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovies.R;
import com.example.mymovies.data.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    // Объект массива отзывов в адаптере
    private ArrayList<Review> reviewAdapterArrayList;

    // Создать ViewHolder с макетом review_item.xml для отзыва
    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    // Загрузить отзывы к фильмам из ячеек массива в позиции ViewHolder равные индексам ячеек массива
    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewAdapterArrayList.get(position);
        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewContent.setText(review.getContent());
    }

    // Получить кол-во отзывов в массиве адаптера
    @Override
    public int getItemCount() {
        return reviewAdapterArrayList.size();
    }

    // Сеттер для установки массива отзывов в адаптере
    public void setReviewAdapterArrayList(ArrayList<Review> reviewAdapterArrayList) {
        this.reviewAdapterArrayList = reviewAdapterArrayList;
        notifyDataSetChanged();
    }

    // ViewHolder(держатель видимых частей) для отзывов
    class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAuthor;
        private TextView textViewContent;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            // Присвоить значение для View с отзывом
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewContent = itemView.findViewById(R.id.textViewContent);
        }
    }
}
