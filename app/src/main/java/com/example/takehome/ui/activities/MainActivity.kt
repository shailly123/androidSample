package com.example.takehome.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.takehome.ui.screens.DashboardContent
import com.example.takehome.ui.screens.RepositoryDetailsScreen
import com.example.takehome.ui.theme.TakeHomeTheme
import com.example.takehome.ui.viewmodels.GithubUserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakeHomeTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val githubUserViewModel: GithubUserViewModel = hiltViewModel()
    NavHost(navController, startDestination = "home") {

        composable("home") { DashboardContent(navController, githubUserViewModel) }
        composable("details/{itemId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.LongType }
            )) {
            RepositoryDetailsScreen(navController, githubUserViewModel)
        }
    }
}

