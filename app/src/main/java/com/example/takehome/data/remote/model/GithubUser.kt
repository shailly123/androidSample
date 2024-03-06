package com.example.takehome.data.remote.model

import com.squareup.moshi.Json
/**
 * Model class for Github User
 */
data class GithubUser(
    @Json(name = "name") val userName : String,
    @Json(name = "avatar_url") val avatarUrl : String?)