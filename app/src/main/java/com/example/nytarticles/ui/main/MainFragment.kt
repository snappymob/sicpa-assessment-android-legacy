package com.example.nytarticles.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytarticles.R
import com.example.nytarticles.databinding.FragmentMainBinding
import com.example.nytarticles.models.ArticlePageArgument
import com.example.nytarticles.models.PopularArticlesType
import com.example.nytarticles.models.Section
import com.example.nytarticles.models.SectionItemIdentifier
import com.example.nytarticles.ui.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(), SectionItemClickListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate

    private lateinit var adapter: SectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Generate list we want to render.
        val items = listOf(
            Section.SectionTitle(getString(R.string.search)),
            Section.SectionItem(SectionItemIdentifier.SearchArticles, getString(R.string.search_articles)),
            Section.SectionTitle(getString(R.string.popular)),
            Section.SectionItem(SectionItemIdentifier.MostViewed, getString(R.string.most_viewed)),
            Section.SectionItem(SectionItemIdentifier.MostShared, getString(R.string.most_shared)),
            Section.SectionItem(SectionItemIdentifier.MostEmailed, getString(R.string.most_emailed))
        )

        with(binding) {
            adapter = SectionAdapter(items, this@MainFragment)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
        }
    }

    override fun onClickSectionItem(identifier: SectionItemIdentifier) {
        when (identifier) {
            SectionItemIdentifier.SearchArticles -> {
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSearchFragment())
            }
            SectionItemIdentifier.MostViewed -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToArticlesFragment(
                        ArticlePageArgument.PopularArticles(
                            PopularArticlesType.MostViewed
                        )
                    )
                )
            }
            SectionItemIdentifier.MostShared -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToArticlesFragment(
                        ArticlePageArgument.PopularArticles(
                            PopularArticlesType.MostShared
                        )
                    )
                )
            }
            SectionItemIdentifier.MostEmailed -> {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToArticlesFragment(
                        ArticlePageArgument.PopularArticles(
                            PopularArticlesType.MostEmailed
                        )
                    )
                )
            }
        }
    }
}
