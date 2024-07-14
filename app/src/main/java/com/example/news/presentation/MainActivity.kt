package com.example.news.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.news.R
import com.example.news.data.model.Articles
import com.example.news.data.utils.State
import com.example.news.presentation.theme.NewsTheme
import com.example.news.presentation.utils.StringUtils.Companion.convertDateTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsTheme {
                val navController = rememberNavController()
                val isDetailScreen = remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        if (isDetailScreen.value) {
                            Icon(
                                modifier = Modifier.clickable {
                                    navController.popBackStack()
                                },
                                imageVector =
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.arrow_back)
                            )
                        }
                        Text(
                            fontSize = 24.sp,
                            text = "News",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(24.dp, 30.dp)
                        )
                    }
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "articleList",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("articleList") {
                            DashboardScreen(navController = navController)
                            isDetailScreen.value = false
                        }
                        composable("cardDetail") {
                            isDetailScreen.value = true
                            val result =
                                navController.previousBackStackEntry?.savedStateHandle?.get<Articles>(
                                    "article"
                                )
                            CardDetailScreen(result)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun SearchBar(viewModel: MainViewModel = hiltViewModel()) {
    var input by remember { mutableStateOf(TextFieldValue()) }

    TextField(leadingIcon = {
        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_icon))
    },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        value = input,
        onValueChange = { newText ->
            MainScope().launch {
                viewModel.searchNews(newText.text)
            }
            input = newText
        },
        label = { Text("Search..") })
}

@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()
) {
    val headline by viewModel.headline.collectAsState()

    Column(modifier = modifier) {
        SearchBar()
        when (val value = headline) {
            is State.Error -> Text(
                text = "Error", modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )

            is State.Success -> NewsList(
                navController,
                value.data.articles.filter { it.urlToImage?.isNotEmpty() == true })

            else -> Text(
                text = "Loading...",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Composable
fun NewsList(navController: NavController, articles: List<Articles>) {
    LazyColumn {
        items(articles) {
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        navController.currentBackStackEntry?.savedStateHandle?.set("article", it)
                        navController.navigate("cardDetail")
                    },
            ) {
                NewsCard(it)
            }
        }
    }
}

@Composable
fun NewsCard(article: Articles?) {
    Column {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            painter = rememberAsyncImagePainter(article?.urlToImage),
            contentDescription = stringResource(R.string.news_image),
            contentScale = ContentScale.Crop,
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                textAlign = TextAlign.Center,
                text = article?.title.toString(),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    lineHeight = TextUnit(20f, TextUnitType.Sp)
                )
            )

            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp),
                text = article?.description.toString(),
                style = MaterialTheme.typography.titleMedium.copy(
                    lineHeight = TextUnit(20f, TextUnitType.Sp)
                )
            )

            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp),
                text = article?.publishedAt?.convertDateTime().toString(),
                style = MaterialTheme.typography.titleSmall.copy(
                    lineHeight = TextUnit(20f, TextUnitType.Sp)
                )
            )
        }
    }
}

@Composable
fun CardDetailScreen(article: Articles?) {
    NewsCard(article = article)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewsTheme {
//        DashboardScreen()
    }
}