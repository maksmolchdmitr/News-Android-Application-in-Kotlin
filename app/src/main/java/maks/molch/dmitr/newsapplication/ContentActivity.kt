package maks.molch.dmitr.newsapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import maks.molch.dmitr.newsapplication.news_client.data.NewsItem
import maks.molch.dmitr.newsapplication.ui.theme.NewsApplicationTheme

class ContentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsItem =
            intent.getParcelableExtra<NewsItem>(NewsItem::class.java.canonicalName)
        setContent {
            if (newsItem != null) {
                ContentScreen(newsItem)
            } else {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun ErrorScreen() {
    Text(text = "Error news handling : (")
}

@Composable
fun ContentScreen(newsItem: NewsItem) {
    NewsApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Content(newsItem)
        }
    }
}

@Composable
fun Content(newsItem: NewsItem) {
    Column {
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
            Text(
                text = toCreatorsText(newsItem.creator),
                fontSize = textUnit,
                fontWeight = FontWeight.Bold,
                modifier = textModifier
                    .weight(1f, true)
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(listOf(newsItem)) {
                Text(text = newsItem.content ?: "Empty")
            }
        }
    }
}

private fun toCreatorsText(creators: List<String>?): String {
    if (creators == null) return ""
    return when (creators.size) {
        0 -> ""
        1 -> "Creator: ${creators[0]}"
        else -> "Creators: ${creators.joinToString(", ")}"
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ContentScreen(
        NewsItem(
            title = "some TITLE",
            creator = arrayListOf("Maks Molchanov"),
            description = null,
            content = """
        @Preview(showBackground = true)
            @Composable
            fun GreetingPreview() {
                val newsData = remember {
                    mutableStateOf(
                        listOf(
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                            NewsItem(
                                "Some title ...!",
                                arrayListOf("Maks Molchanov", "Noname"),
                                "Som description text that is might be slowly big and continiously!...",
                                ""${'"'}
                            Very long text content... 
                            Jetpack Compose has given a new perspective to UI development in Android with its declarative programming model. Composable functions are the building blocks of this framework, and modifiers are the essential tools used to design the layout, appearance, and behavior of these Composable functions.
                
                            In this blog post, we will focus on the Modifier.weight function and its usage. The Modifier.weight function is a part of the Jetpack Compose foundation layout. It helps distribute the available space among the children of a Row or Column Composable.
                
                            What is Modifier.weight?
                            Modifier.weight is a function that allocates available space along the main axis (horizontal for Row and vertical for Column) to its child elements. The distribution is done in proportion to the weightage assigned to each child. It’s similar to the concept of “weight” in LinearLayout in traditional Android development.
                
                            The Modifier.weight function takes a single Float parameter, weight. The space each child occupies is proportional to its weight divided by the total weight of all children.
                            Modifier Weight Examples
                            Let’s start with a simple example that uses Modifier.weight in a Row.
                            
                            In the above example, a Row composable has two Box children. The available horizontal space is split between them according to their weight, i.e., in a 1:2 ratio. The Box with Modifier.weight(1f) will occupy a third of the horizontal space, and the Box with Modifier.weight(2f) will fill the remaining two-thirds.
                            
                        ""${'"'}.trimIndent()
                            ),
                        )
                    )
                }
                NewsList(newsData)
            }

    """.trimIndent()
        )
    )
}