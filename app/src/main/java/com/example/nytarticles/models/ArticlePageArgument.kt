package com.example.nytarticles.models

import android.os.Parcelable
import com.example.nytarticles.ui.articles.ArticlesFragment
import kotlinx.parcelize.Parcelize

/**
 * Serves a an argument to [ArticlesFragment]
 */
sealed class ArticlePageArgument : Parcelable {
    /**
     * Represents a request to view popular Articles
     *
     * @property type [PopularArticlesType] specifying the kind of popular Articles that is being requested
     */
    @Parcelize
    class PopularArticles(val type: PopularArticlesType) : ArticlePageArgument(), Parcelable

    /**
     * Represents a request to search Articles
     *
     * @property query [String] representing search query
     */
    @Parcelize
    class SearchArticles(val query: String) : ArticlePageArgument(), Parcelable
}
