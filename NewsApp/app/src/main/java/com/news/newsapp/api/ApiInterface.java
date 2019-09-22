package com.news.newsapp.api;

import com.news.newsapp.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headLines")
    Call<News> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("top-headLines")
    Call<News> getCategoryNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );
}
