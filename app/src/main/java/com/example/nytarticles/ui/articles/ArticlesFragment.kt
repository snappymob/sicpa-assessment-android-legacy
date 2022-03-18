package com.example.nytarticles.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nytarticles.databinding.FragmentArticlesBinding
import com.example.nytarticles.models.State
import com.example.nytarticles.ui.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticlesFragment : BaseFragment<FragmentArticlesBinding>() {

    private val viewModel: ArticlesViewModel by viewModels()
    private lateinit var adapter: ArticlesAdapter
    private val args: ArticlesFragmentArgs by navArgs()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentArticlesBinding
        get() = FragmentArticlesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setInitialData(args.articleFragmentArgument)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    binding.progressBarWrapper.isVisible = state is State.Loading
                    binding.errorLayoutWrapper.isVisible = state is State.LoadingFailed

                    when (state) {
                        is State.Loaded -> {
                            adapter = ArticlesAdapter(state.data)
                            binding.recyclerView.layoutManager =
                                LinearLayoutManager(requireContext())
                            binding.recyclerView.adapter = adapter
                        }
                        State.Loading -> Unit
                        State.LoadingFailed -> Unit
                    }
                }
            }
        }

        binding.errorLayout.retryButton.setOnClickListener {
            viewModel.retry()
        }
    }
}
