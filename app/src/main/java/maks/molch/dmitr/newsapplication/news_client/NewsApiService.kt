package maks.molch.dmitr.newsapplication.news_client

import maks.molch.dmitr.newsapplication.news_client.data.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/api/1/news")
    suspend fun getNewsWithLanguage(
        @Query("apikey") apiKey: String,
        @Query("q") query: String,
        @Query("language") language: String
    ): NewsResponse

    @GET("/api/1/news")
    suspend fun getNews(
        @Query("apikey") apiKey: String,
        @Query("q") query: String
    ): NewsResponse
}
