package com.mu.tote2026.presentation.screen.admin.email

sealed class AdminEmailEvent {
    data class OnEmailChange(val email: String) : AdminEmailEvent()
    data object OnSave : AdminEmailEvent()
}
