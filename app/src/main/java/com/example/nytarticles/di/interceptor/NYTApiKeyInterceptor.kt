package com.example.nytarticles.di.interceptor

import com.example.nytarticles.BuildConfig
import com.example.nytarticles.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Will intercept network requests and add a NYT Api key.
 */
class NYTApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val url = request.url.newBuilder().addQueryParameter("api-key", BuildConfig.NYT_API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}
