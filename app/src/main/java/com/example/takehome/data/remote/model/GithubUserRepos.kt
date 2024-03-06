package com.example.takehome.data.remote.model

import com.squareup.moshi.Json

/**
 * Model class for Github User Repository
 */
data class GithubUserRepos(
    @Json(name = "id") val id :Long,
    @Json(name = "name") val name : String,
    @Json(name = "description") val description : String?,
    @Json(name = "updated_at") val updatedAt : String,
    @Json(name = "stargazers_count") val stargazersCount : Int,
    @Json(name = "forks") val forks : Int
)