package com.example.nytarticles.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.nytarticles.databinding.FragmentSearchBinding
import com.example.nytarticles.models.ArticlePageArgument
import com.example.nytarticles.ui.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchButton.setOnClickListener {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToArticlesFragment(
                        ArticlePageArgument.SearchArticles(
                            textInputField.text.toString()
                        )
                    )
                )
            }

            textInputField.addTextChangedListener {
                // Optionally if we have some complex validation to handle - do it in a viewModel.
                searchButton.isEnabled = !it.isNullOrBlank()
            }
        }
    }
}
