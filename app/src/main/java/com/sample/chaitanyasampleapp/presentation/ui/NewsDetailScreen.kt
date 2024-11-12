package com.sample.chaitanyasampleapp.presentation.ui// ui/NewsDetailScreen.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.sample.chaitanyasampleapp.data.model.Article

@Composable
fun NewsDetailScreen(selectedModel: Article) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedModel.urlToImage?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = selectedModel.title ?: "No title", fontWeight = FontWeight.Bold)
        Text(text = "By ${selectedModel.author}", color = Color.Gray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = selectedModel.description ?: "No description")
    }
}
