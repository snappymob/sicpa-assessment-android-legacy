package com.example.nytarticles.models

import java.util.*

/**
 * Represents a standardised Article entity
 */
interface Article {
    val title: String
    val publishedAt: Date
}
