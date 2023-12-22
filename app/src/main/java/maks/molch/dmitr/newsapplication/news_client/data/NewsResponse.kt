package maks.molch.dmitr.newsapplication.news_client.data

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val results: List<NewsItem>,
    val nextPage: String?
)
