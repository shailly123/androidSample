package com.example.takehome.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.takehome.data.remote.model.GithubUserRepos
import com.example.takehome.ui.viewmodels.GithubUserViewModel

@SuppressLint("NewApi")
@Composable
fun RepositoryDetailsScreen (
    navController: NavHostController,
    githubUserViewModel: GithubUserViewModel
){
   val itemId  = navController.currentBackStackEntry?.arguments?.getLong("itemId")
    val repo : GithubUserRepos? = itemId?.let { githubUserViewModel.getItemById(it) }
    Column(Modifier.fillMaxWidth()) {
        Text(text = "Repo ID : ${repo?.id}")
        Text(text = "Repo Name : ${repo?.name}")
        Text(text = "Repo Description : ${repo?.description}")
        Text(text = "Repo Updated At : ${repo?.updatedAt}")
        Text(text = "Repo Stargazers Count : ${repo?.stargazersCount}")
        Text(text = "Repo Forks Count : ${repo?.forks}")
    }
}