package com.sample.chaitanyasampleapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sample.chaitanyasampleapp.presentation.ui.NewsScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()

        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            NewsScreen(hiltViewModel())
        }
        composable("details/{entryId}") { backStackEntry ->
            val entryId = backStackEntry.arguments?.getString("entryId")?.toInt() ?: 0
           // ApiDetailScreen(viewModel = ApiDetailViewModel(entryId = entryId))
        }
    }
}
