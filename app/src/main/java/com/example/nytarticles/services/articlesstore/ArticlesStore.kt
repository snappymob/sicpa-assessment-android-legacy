package com.example.nytarticles.services.articlesstore

import com.example.nytarticles.models.PopularArticlesResponse
import com.example.nytarticles.models.PopularArticlesType
import com.example.nytarticles.models.SearchArticlesResponse
import kotlinx.coroutines.flow.Flow

interface ArticlesStore {

    suspend fun fetchMostPopularArticles(type: PopularArticlesType): Flow<Result<PopularArticlesResponse>>

    suspend fun fetchArticleSearchResults(query: String): Flow<Result<SearchArticlesResponse>>
}
