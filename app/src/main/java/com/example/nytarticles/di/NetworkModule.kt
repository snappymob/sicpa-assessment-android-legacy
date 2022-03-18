package com.example.nytarticles.di

import com.example.nytarticles.BuildConfig
import com.example.nytarticles.di.interceptor.NYTApiKeyInterceptor
import com.example.nytarticles.services.NetworkRequestManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NYTApiKeyInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NYTApiKeyInterceptorRetrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    @NYTApiKeyInterceptorRetrofit
    fun provideAuthInterceptorRetrofit(
        @NYTApiKeyInterceptorOkHttpClient client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NYT_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @NYTApiKeyInterceptorOkHttpClient
    fun provideNYTInterceptorOkHttpClient(networkInterceptor: NYTApiKeyInterceptor): OkHttpClient {
        val connectTimeout: Long = 60
        val readTimeout: Long = 60

        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
            .addInterceptor(networkInterceptor)

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideNYTApiKeyInterceptor(): NYTApiKeyInterceptor {
        return NYTApiKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        return GsonConverterFactory.create(gsonBuilder.create())
    }

    @Provides
    @Singleton
    fun provideNetworkRequestManager(): NetworkRequestManager {
        return NetworkRequestManager()
    }
}
