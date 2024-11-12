package com.sample.chaitanyasampleapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.sample.chaitanyasampleapp.data.model.Article
import com.sample.chaitanyasampleapp.presentation.viewmodel.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel(), navController: NavHostController) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        if (state.articles.isEmpty()) viewModel.fetchTopHeadlines()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            state.error != null -> Text(text = state.error!!, color = Color.Red, modifier = Modifier.align(Alignment.Center))
            else -> {
                NewsList(articles = state.articles,navController,viewModel)
            }
        }
    }
}

@Composable
fun NewsList(articles: List<Article>, navController: NavHostController, viewModel: NewsViewModel) {
    LazyColumn(modifier = Modifier.fillMaxSize()
        .background(Color.LightGray)
    ) {
        items(articles) { article -> NewsItem(article = article,
            onClick = {
                // Navigate to the detail screen
                navController.navigate("detail/${article.author}")
        })
        }
    }
}

@Composable
fun NewsItem(article: Article,onClick:()->Unit) {
    Box (modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)){


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp), shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color.White).clickable { onClick() }
            ) {
                article.urlToImage?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(4.dp)
                    )

                    Text(
                        text = article.title ?: "No title",
                        modifier = Modifier.padding(5.dp),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = article.description ?: "No description",
                        modifier = Modifier.padding(5.dp),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}



