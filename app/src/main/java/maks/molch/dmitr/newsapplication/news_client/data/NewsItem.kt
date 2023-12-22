package maks.molch.dmitr.newsapplication.news_client.data

data class NewsItem(
    val title: String,
    val creator: List<String>,
    val description: String,
    val content: String,
)

