package com.example.nytarticles

import com.example.nytarticles.models.ArticlePageArgument
import com.example.nytarticles.models.PopularArticle
import com.example.nytarticles.models.PopularArticlesResponse
import com.example.nytarticles.models.PopularArticlesType
import com.example.nytarticles.models.SearchArticle
import com.example.nytarticles.models.SearchArticlesResponse
import com.example.nytarticles.models.SearchArticlesResponseObject
import com.example.nytarticles.models.State
import com.example.nytarticles.services.articlesstore.ArticlesStore
import com.example.nytarticles.ui.articles.ArticlesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.times
import java.util.*

@ExperimentalCoroutinesApi
class ArticlesViewModelTest {

    private lateinit var articlesStore: ArticlesStore

    private lateinit var viewModel: ArticlesViewModel

    private val samplePopularArticlesResponse = PopularArticlesResponse(
        listOf(
            PopularArticle(
                "",
                Date()
            ),
            PopularArticle(
                "",
                Date()
            )
        )
    )

    private val sampleSearchResponse = SearchArticlesResponse(
        SearchArticlesResponseObject(
            listOf(
                SearchArticle(
                    "",
                    Date()
                ),
                SearchArticle(
                    "",
                    Date()
                )
            )
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        articlesStore = Mockito.mock(ArticlesStore::class.java)
        viewModel = ArticlesViewModel(articlesStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `set MostEmailed popular article initial data and calls fetchMostPopularArticles correctly`(): Unit = runTest {
        val popularArticleType = PopularArticlesType.MostEmailed
        `when`(articlesStore.fetchMostPopularArticles(popularArticleType)).thenReturn(flow {
            emit(Result.failure(Exception()))
        })
        viewModel.setInitialData(ArticlePageArgument.PopularArticles(popularArticleType))
        Mockito.verify(articlesStore, times(1)).fetchMostPopularArticles(popularArticleType)
        Mockito.verify(articlesStore, times(0)).fetchArticleSearchResults(anyOrNull())
    }

    @Test
    fun `set searchArticles initial data and calls calls fetchArticleSearchResults correctly`(): Unit = runTest {
        val query = "bla"
        `when`(articlesStore.fetchArticleSearchResults(query)).thenReturn(flow {
            emit(Result.failure(Exception()))
        })
        viewModel.setInitialData(ArticlePageArgument.SearchArticles(query))
        Mockito.verify(articlesStore, times(1)).fetchArticleSearchResults(query)
        Mockito.verify(articlesStore, times(0)).fetchMostPopularArticles(anyOrNull())
    }

    @Test
    fun `initial state of viewModel is loading`(): Unit = runTest {
        assert(viewModel.state.value == State.Loading)
    }

    @Test
    fun `error response from api and emits LoadingFailed`(): Unit = runTest {
        assert(viewModel.state.value == State.Loading)

        val popularArticleType = PopularArticlesType.MostEmailed
        `when`(articlesStore.fetchMostPopularArticles(popularArticleType)).thenReturn(flow {
            emit(Result.failure(Exception()))
        })
        viewModel.setInitialData(ArticlePageArgument.PopularArticles(popularArticleType))
        assert(viewModel.state.value == State.LoadingFailed)
    }

    @Test
    fun `successful response from api and emits correct Loaded state`(): Unit = runTest {
        assert(viewModel.state.value == State.Loading)

        val popularArticleType = PopularArticlesType.MostEmailed
        `when`(articlesStore.fetchMostPopularArticles(popularArticleType)).thenReturn(flow {
            emit(Result.success(samplePopularArticlesResponse))
        })
        viewModel.setInitialData(ArticlePageArgument.PopularArticles(popularArticleType))
        assert(viewModel.state.value is State.Loaded)
        assert((viewModel.state.value as State.Loaded).data == samplePopularArticlesResponse.articles)
    }
}
