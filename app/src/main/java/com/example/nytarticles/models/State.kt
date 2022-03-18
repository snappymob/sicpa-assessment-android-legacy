package com.example.nytarticles.models

/**
 * Various states for UI to handle.
 */
sealed class State<out R> {
    class Loaded<out T>(val data: T) : State<T>()
    object Loading : State<Nothing>()
    object LoadingFailed : State<Nothing>()
}
