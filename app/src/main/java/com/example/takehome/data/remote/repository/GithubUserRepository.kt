package com.example.takehome.data.remote.repository

import com.example.takehome.data.remote.api.ApiService
import com.example.takehome.data.remote.model.GithubUser
import com.example.takehome.data.remote.model.GithubUserRepos
import javax.inject.Inject

/**
 * Remote Repository for fetching Github user profile and repositories
 */
class GithubUserRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchGithubUser(userId : String) : GithubUser{
        return apiService.getGithubUser(userId)
    }
    suspend fun fetchGithubUserRepos(userId : String) : List<GithubUserRepos>{
        return apiService.getGithubUserRepos(userId)
    }
}