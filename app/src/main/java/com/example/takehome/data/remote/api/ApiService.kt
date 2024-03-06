package com.example.takehome.data.remote.api

import com.example.takehome.data.remote.model.GithubUser
import com.example.takehome.data.remote.model.GithubUserRepos
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API Interface for all the Retrofit calls
 */
interface ApiService {

    @GET("users/{userId}")
    suspend fun getGithubUser(@Path("userId") userId : String) : GithubUser

    @GET("users/{userId}/repos")
    suspend fun getGithubUserRepos(@Path("userId") userId : String) : List<GithubUserRepos>
}