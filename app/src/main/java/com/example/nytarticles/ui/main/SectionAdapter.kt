package com.example.nytarticles.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nytarticles.databinding.ListItemSectionItemBinding
import com.example.nytarticles.databinding.ListItemSectionTitleBinding
import com.example.nytarticles.models.Section

class SectionAdapter(private val items: List<Section>, private val listener: SectionItemClickListener) :
    RecyclerView.Adapter<SectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == ITEM_TYPE_SECTION_TITLE) {
            val binding = ListItemSectionTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TitleViewHolder(binding)
        } else {
            val binding = ListItemSectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                val item = items[position] as Section.SectionTitle
                holder.binding.titleTextView.text = item.title
            }
            is ItemViewHolder -> {
                val item = items[position] as Section.SectionItem
                holder.binding.titleTextView.text = item.title
                holder.binding.root.setOnClickListener {
                    listener.onClickSectionItem(item.identifier)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Section.SectionTitle -> ITEM_TYPE_SECTION_TITLE
            is Section.SectionItem -> ITEM_TYPE_SECTION_CATEGORY_ITEM
        }
    }

    abstract inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    inner class TitleViewHolder(val binding: ListItemSectionTitleBinding) : ViewHolder(binding.root)

    inner class ItemViewHolder(val binding: ListItemSectionItemBinding) : ViewHolder(binding.root)

    companion object {
        const val ITEM_TYPE_SECTION_TITLE = 0
        const val ITEM_TYPE_SECTION_CATEGORY_ITEM = 1
    }
}
