package com.example.nytarticles.di

import com.example.nytarticles.api.PopularArticlesApi
import com.example.nytarticles.api.SearchArticlesApi
import com.example.nytarticles.services.NetworkRequestManager
import com.example.nytarticles.services.articlesstore.ArticlesStore
import com.example.nytarticles.services.articlesstore.DefaultArticlesStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StoreModule {

    @Provides
    @Singleton
    fun provideArticleStore(
        popularArticlesApi: PopularArticlesApi,
        searchArticlesApi: SearchArticlesApi,
        networkRequestManager: NetworkRequestManager
    ): ArticlesStore {
        return DefaultArticlesStore(
            popularArticlesApi,
            searchArticlesApi,
            networkRequestManager
        )
    }
}
