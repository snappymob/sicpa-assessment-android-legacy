package com.example.nytarticles.services

import retrofit2.Response

/**
 * Handle API call results before passing up to the application.
 *
 * Returns API call result wrapped in a [Result] object, to provide a consistent and convenient way for application to handle network responses.
 *
 * We can also convert API errors to application specific errors if we want to handle those.
 *
 */
class NetworkRequestManager {
    suspend inline fun <reified T> apiRequest(crossinline apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            val response = apiCall.invoke()

            return if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    Result.success(body)
                } ?: Result.failure(Exception())
            } else {
                Result.failure(Exception())
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
