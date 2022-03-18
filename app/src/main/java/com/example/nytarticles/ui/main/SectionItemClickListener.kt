package com.example.nytarticles.ui.main

import com.example.nytarticles.models.SectionItemIdentifier

interface SectionItemClickListener {
    fun onClickSectionItem(identifier: SectionItemIdentifier)
}
