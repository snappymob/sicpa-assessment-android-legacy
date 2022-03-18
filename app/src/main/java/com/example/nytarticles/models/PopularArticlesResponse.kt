package com.example.nytarticles.models

import com.example.nytarticles.api.PopularArticlesApi
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Represents a response object for [PopularArticlesApi]
 */
data class PopularArticlesResponse(
    @SerializedName("results")
    val articles: List<PopularArticle>
)

/**
 * Article object inside [PopularArticlesResponse]
 */
data class PopularArticle(
    override val title: String,
    @SerializedName("published_date")
    override val publishedAt: Date
) : Article
