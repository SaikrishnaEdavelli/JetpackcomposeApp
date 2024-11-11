package com.sample.chaitanyasampleapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.sample.chaitanyasampleapp.data.model.Article
import com.sample.chaitanyasampleapp.presentation.viewmodel.NewsViewModel

@Composable
fun NewsScreen(viewModel: NewsViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            state.error != null -> Text(text = state.error!!, color = Color.Red, modifier = Modifier.align(Alignment.Center))
            else -> {
                NewsList(articles = state.articles)
            }
        }
    }
}

@Composable
fun NewsList(articles: List<Article>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(articles) { article ->
            NewsItem(article = article)
        }
    }
}

@Composable
fun NewsItem(article: Article) {
    Column(modifier = Modifier.padding(16.dp)) {
        article.urlToImage?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
        Text(text = article.title ?: "No title", fontWeight = FontWeight.Bold)
        Text(text = article.description ?: "No description", color = Color.Gray)
    }
}
