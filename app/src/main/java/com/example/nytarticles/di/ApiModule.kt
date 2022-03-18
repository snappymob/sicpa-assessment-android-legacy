package com.example.nytarticles.di

import com.example.nytarticles.api.PopularArticlesApi
import com.example.nytarticles.api.SearchArticlesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideSearchArticlesApi(@NYTApiKeyInterceptorRetrofit retrofit: Retrofit): SearchArticlesApi {
        return retrofit.create(SearchArticlesApi::class.java)
    }

    @Provides
    @Singleton
    fun providePopularArticlesApi(@NYTApiKeyInterceptorRetrofit retrofit: Retrofit): PopularArticlesApi {
        return retrofit.create(PopularArticlesApi::class.java)
    }
}
