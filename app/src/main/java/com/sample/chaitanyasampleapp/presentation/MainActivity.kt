package com.sample.chaitanyasampleapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.chaitanyasampleapp.presentation.ui.NewsDetailScreen
import com.sample.chaitanyasampleapp.presentation.ui.NewsScreen
import com.sample.chaitanyasampleapp.presentation.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Toolbar(onBackPressed = { finish() }, "Movies App")

        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NewsScreen(hiltViewModel(), navController)
        }
        composable("detail/{author}") { backStackEntry ->
            val viewModel: NewsViewModel = hiltViewModel()
            val articleId = backStackEntry.arguments?.getString("author") ?: ""
            LaunchedEffect(articleId) {
            }
            viewModel.getArticleById(articleId)


            // Collect the article state
            val selectedArticle by viewModel.selectedArticle.collectAsState()

            if (selectedArticle == null) {
                Text("Loading...")
            } else {
                NewsDetailScreen(selectedArticle!!)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(onBackPressed: () -> Unit, title: String) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title, color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Blue,
                )

            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            AppNavigation()
        }
    }
}





