package com.example.mymovies.data;

public class Review {

    private String author;
    private String content;

    // Конструктор для создания объекта Review
    public Review(String author, String content) {
        this.author = author;
        this.content = content;
    }

    // Геттеры для получения значений объекта Review
    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    // Сеттеры для установки значений объекта Review
    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
