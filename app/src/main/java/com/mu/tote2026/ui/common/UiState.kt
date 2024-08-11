package com.mu.tote2026.ui.common

sealed class UiState<out T> {
    data object Default : UiState<Nothing>()
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val error: String) : UiState<Nothing>()
}
