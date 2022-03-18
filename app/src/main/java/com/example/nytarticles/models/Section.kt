package com.example.nytarticles.models

import com.example.nytarticles.ui.main.MainFragment


/**
 * Represents a section for building UI inside [MainFragment]
 */
sealed class Section {
    class SectionTitle(val title: String) : Section()
    class SectionItem(val identifier: SectionItemIdentifier, val title: String) : Section()
}

enum class SectionItemIdentifier {
    SearchArticles,
    MostViewed,
    MostShared,
    MostEmailed
}
