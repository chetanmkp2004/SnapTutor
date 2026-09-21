package com.snaptutor.app.core.state

/**
 * Generic sealed class for UI state management.
 * Every feature ViewModel exposes a single StateFlow<UiState<T>>.
 * Composables render Loading/Error states using core LoadingView/ErrorView components.
 */
sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
