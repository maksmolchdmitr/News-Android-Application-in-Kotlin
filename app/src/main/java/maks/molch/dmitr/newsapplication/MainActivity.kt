package maks.molch.dmitr.newsapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
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
        if (newsData.value.isEmpty()) {
            Text(
                text = "Empty list of news : (",
                fontSize = TextUnit(5f, TextUnitType.Em)
            )
        } else {
            LazyColumn {
                items(newsData.value) { newsItem ->
                    NewsItemCard(newsItem = newsItem)
                }
            }
        }
    }

    @Composable
    fun NewsItemCard(newsItem: NewsItem) {
        Column(
            Modifier
                .border(3.dp, Color.Black, RectangleShape)
                .fillMaxWidth()
                .clickable {
                    onItemCardClick(newsItem)
                },
        ) {
            Row(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                val textModifier = Modifier
                    .padding(3.dp)
                    .border(2.dp, Color.Gray, CircleShape)
                    .background(Color.LightGray, CircleShape)
                    .fillMaxWidth()
                    .padding(30.dp)
                val textUnit = TextUnit(3f, TextUnitType.Em)
                Text(
                    text = "Title: ${newsItem.title}",
                    fontSize = textUnit,
                    fontWeight = FontWeight.Bold,
                    modifier = textModifier
                        .weight(2f, true)
                )
                newsItem.creator?.let {
                    Text(
                        text = toCreatorsText(it),
                        fontSize = textUnit,
                        fontWeight = FontWeight.Bold,
                        modifier = textModifier
                            .weight(1f, true)
                    )
                }
            }
            newsItem.description?.let {
                Text(
                    text = it,
                    color = Color.White,
                    fontSize = TextUnit(25f, TextUnitType.Sp),
                    modifier = Modifier
                        .padding(all = 5.dp)
                        .background(Color.DarkGray)
                )
            }
        }
    }

    private fun onItemCardClick(newsItem: NewsItem) {
        val bundle = Bundle().apply {
            putParcelable(NewsItem::class.java.canonicalName, newsItem)
        }
        val intent = Intent(this, ContentActivity::class.java).apply {
            putExtras(bundle)
        }
        startActivity(intent)
    }

    private fun toCreatorsText(creators: List<String>): String {
        return when (creators.size) {
            0 -> ""
            1 -> "Creator: ${creators[0]}"
            else -> "Creators: ${creators.joinToString(", ")}"
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

}