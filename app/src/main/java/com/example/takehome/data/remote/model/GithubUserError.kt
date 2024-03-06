package com.example.takehome.data.remote.model

import com.squareup.moshi.Json
/**
 * Model class for Github User Error
 */
data class GithubUserError (
   @Json(name = "message") val message : String,
   @Json(name = "documentation_url") val documentationUrl : String)