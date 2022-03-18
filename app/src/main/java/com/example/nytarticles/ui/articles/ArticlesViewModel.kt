package com.example.nytarticles.ui.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytarticles.models.Article
import com.example.nytarticles.models.ArticlePageArgument
import com.example.nytarticles.models.PopularArticlesType
import com.example.nytarticles.models.State
import com.example.nytarticles.services.articlesstore.ArticlesStore
import com.example.nytarticles.services.articlesstore.DefaultArticlesStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val articlesStore: ArticlesStore
) : ViewModel() {

    private val _state: MutableStateFlow<State<List<Article>>> = MutableStateFlow(State.Loading)
    val state = _state.asStateFlow()

    private lateinit var articlePageArgument: ArticlePageArgument

    fun setInitialData(articlePageArgument: ArticlePageArgument) {
        this.articlePageArgument = articlePageArgument
        fetchData()
    }

    private fun fetchData() {
        // Decide what to do based on argument passed.
        when (articlePageArgument) {
            is ArticlePageArgument.PopularArticles -> fetchPopularArticles((articlePageArgument as ArticlePageArgument.PopularArticles).type)
            is ArticlePageArgument.SearchArticles -> searchArticles((articlePageArgument as ArticlePageArgument.SearchArticles).query)
        }
    }

    private fun searchArticles(query: String) {
        viewModelScope.launch {
            val result = articlesStore.fetchArticleSearchResults(query).first()

            if (result.isSuccess && result.getOrNull() != null) {
                _state.emit(State.Loaded(result.getOrNull()!!.response.docs))
            } else {
                _state.emit(State.LoadingFailed)
            }
        }
    }

    private fun fetchPopularArticles(popularArticleType: PopularArticlesType) {
        viewModelScope.launch {
            val result = articlesStore.fetchMostPopularArticles(popularArticleType).first()

            if (result.isSuccess && result.getOrNull() != null) {
                _state.emit(State.Loaded(result.getOrNull()!!.articles))
            } else {
                _state.emit(State.LoadingFailed)
            }
        }
    }

    fun retry() {
        fetchData()
    }
}
