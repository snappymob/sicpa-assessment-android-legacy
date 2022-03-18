package com.example.nytarticles.api

import com.example.nytarticles.models.SearchArticlesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchArticlesApi {

    @GET("search/v2/articlesearch.json")
    suspend fun searchArticles(@Query("q") query: String): Response<SearchArticlesResponse>
}
