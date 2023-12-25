package maks.molch.dmitr.newsapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import maks.molch.dmitr.newsapplication.news_client.NewsApiClient
import maks.molch.dmitr.newsapplication.news_client.data.NewsItem
import maks.molch.dmitr.newsapplication.ui.theme.NewsApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    NewsApplicationTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                val newsData = remember { mutableStateOf(emptyList<NewsItem>()) }
                UserInput(newsData)
                NewsOutput(newsData)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UserInput(newsData = remember { mutableStateOf(emptyList()) })
}

@Composable
fun UserInput(newsData: MutableState<List<NewsItem>>) {
    val userInputValue = remember { mutableStateOf(TextFieldValue()) }
    val languagesState = remember { hashSetOf<String>() }
    Row(
        Modifier
            .background(color = Color.Blue)
            .height(128.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier.weight(3f)
        ) {
            KeyWordsTextField(userInputValue)
        }
        Column(
            Modifier
                .weight(1.5f)
                .fillMaxSize()
        ) {
            LanguageCheckBox(languageName = "ru", languagesState)
            LanguageCheckBox(languageName = "en", languagesState)
        }
        Column(
            Modifier.weight(1.5f)
        ) {
            SearchButton(userInputValue, languagesState, newsData)
        }
    }
}

@Composable
fun SearchButton(
    userInputValue: MutableState<TextFieldValue>,
    languagesState: HashSet<String>,
    newsData: MutableState<List<NewsItem>>
) {
    Button(
        onClick = {
            onSearch(
                userInputValue.value.text,
                languagesState,
                newsData
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Find")
    }
}

@Composable
fun KeyWordsTextField(
    userInputValue: MutableState<TextFieldValue>
) {
    TextField(
        value = userInputValue.value,
        onValueChange = {
            userInputValue.value = it
        },
        placeholder = { Text(text = "Enter key words : )") },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun LanguageCheckBox(
    languageName: String,
    languagesState: HashSet<String>
) {
    val languageState = remember { mutableStateOf(false) }
    Row {
        Checkbox(
            checked = languageState.value,
            onCheckedChange = {
                languageState.value = it
                if (languageState.value) {
                    languagesState.add(languageName)
                } else {
                    languagesState.remove(languageName)
                }
            },
            modifier = Modifier
                .padding(5.dp)
                .background(color = Color.Cyan),
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xff, 0xb6, 0xc1),
                checkmarkColor = Color.Red
            )
        )
        Text(languageName, fontSize = 22.sp)
    }
}

@Composable
fun NewsOutput(newsData: MutableState<List<NewsItem>>) {
    NewsList(newsData)
}

@Composable
fun NewsList(newsData: MutableState<List<NewsItem>>) {
    LazyColumn {
        items(newsData.value) { newsItem ->
            NewsItemCard(newsItem = newsItem)
        }
    }
}

@Composable
fun NewsItemCard(newsItem: NewsItem) {
    Column {
        Text(
            text = "Title: ${newsItem.title}",
            modifier = Modifier
                .padding(all = 10.dp)
                .background(Color.DarkGray)
        )
        Text(
            text = "Author: ${newsItem.creator}",
            color = Color.Black,
            modifier = Modifier
                .padding(all = 10.dp)
                .background(Color.DarkGray)
        )
        Text(
            text = "Description: ${newsItem.description}",
            color = Color.Black,
            modifier = Modifier
                .padding(all = 5.dp)
                .background(Color.DarkGray)
        )
        Text(
            newsItem.content,
            color = Color.White,
            modifier = Modifier
                .padding(3.dp)
                .background(Color.DarkGray)
        )
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun onSearch(
    keyWords: String,
    languageState: Set<String>,
    newsData: MutableState<List<NewsItem>>
) {
    Log.d("", "Languages: ${languageState.joinToString(", ")}")
    GlobalScope.launch {
        newsData.value = NewsApiClient.getNews(
            keyWords,
            languageState
        )
    }
}