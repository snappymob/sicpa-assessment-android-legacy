package com.example.nytarticles.services.articlesstore

import com.example.nytarticles.api.PopularArticlesApi
import com.example.nytarticles.api.SearchArticlesApi
import com.example.nytarticles.models.PopularArticlesResponse
import com.example.nytarticles.models.PopularArticlesType
import com.example.nytarticles.models.SearchArticlesResponse
import com.example.nytarticles.services.NetworkRequestManager
import com.example.nytarticles.utils.Constants.Api.Companion.PopularArticlesApiDefaultPeriod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultArticlesStore @Inject constructor(
    private val popularArticlesApi: PopularArticlesApi,
    private val searchArticlesApi: SearchArticlesApi,
    private val networkRequestManager: NetworkRequestManager
) : ArticlesStore {

    override suspend fun fetchMostPopularArticles(type: PopularArticlesType): Flow<Result<PopularArticlesResponse>> = flow {
        val result = networkRequestManager.apiRequest {
            when (type) {
                PopularArticlesType.MostViewed -> popularArticlesApi.getMostViewedArticles(PopularArticlesApiDefaultPeriod)
                PopularArticlesType.MostEmailed -> popularArticlesApi.getMostEmailedArticles(PopularArticlesApiDefaultPeriod)
                PopularArticlesType.MostShared -> popularArticlesApi.getMostSharedArticles(PopularArticlesApiDefaultPeriod)
            }
        }
        emit(result)
    }

    override suspend fun fetchArticleSearchResults(query: String): Flow<Result<SearchArticlesResponse>> = flow {
        val result = networkRequestManager.apiRequest {
            searchArticlesApi.searchArticles(query)
        }
        emit(result)
    }
}
