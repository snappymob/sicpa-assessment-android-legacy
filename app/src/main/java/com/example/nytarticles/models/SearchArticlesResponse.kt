package com.example.nytarticles.models

import com.example.nytarticles.api.SearchArticlesApi
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Represents a response object for [SearchArticlesApi]
 */
data class SearchArticlesResponse(
    val response: SearchArticlesResponseObject
)

data class SearchArticlesResponseObject(
    val docs: List<SearchArticle>
)

/**
 * Article object inside [SearchArticlesResponse]
 */
data class SearchArticle(
    @SerializedName("abstract")
    override val title: String,
    @SerializedName("pub_date")
    override val publishedAt: Date
) : Article
