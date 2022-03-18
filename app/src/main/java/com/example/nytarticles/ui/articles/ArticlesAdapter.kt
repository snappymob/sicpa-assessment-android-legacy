package com.example.nytarticles.ui.articles

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nytarticles.databinding.ListItemArticleBinding
import com.example.nytarticles.models.Article
import com.example.nytarticles.utils.DateUtils

class ArticlesAdapter(private val items: List<Article>) :
    RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.titleTextView.text = item.title
        holder.binding.datePublishedTextView.text = DateUtils.toDayMonthYearFormat(item.publishedAt)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(val binding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root)
}
