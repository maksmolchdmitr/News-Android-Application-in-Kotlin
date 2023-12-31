package maks.molch.dmitr.newsapplication.news_client

import maks.molch.dmitr.newsapplication.news_client.data.NewsItem
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NewsApiClient {
    private val okHttpClient = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsdata.io")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val service = retrofit.create(NewsApiService::class.java)

    suspend fun getNews(keyWords: String, languages: Set<String>): List<NewsItem> {
        val apiKey = "pub_35198b7e19e89388ef43fb0cb757f83b68b51"
        val nonEmptyKeyWords = keyWords.ifEmpty { "News" }
        val response = if (languages.isNotEmpty()) service.getNewsWithLanguage(
            apiKey,
            nonEmptyKeyWords,
            languages.joinToString(",")
        ) else service.getNews(apiKey, nonEmptyKeyWords)
        if (response.status == "success") {
            return response.results
        } else {
            throw Exception("Failed to fetch news")
        }
    }
}