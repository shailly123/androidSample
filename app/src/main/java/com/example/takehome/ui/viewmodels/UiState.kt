package com.example.takehome.ui.viewmodels

import com.example.takehome.data.remote.model.GithubUser
import com.example.takehome.data.remote.model.GithubUserRepos

/**
 * Data class to maintain UI State
 */
data class UiState (
    var isLoading : Boolean = false,
    val githubUser : GithubUser?,
    val userRepos : List<GithubUserRepos> = listOf(),
    val error : String?
)