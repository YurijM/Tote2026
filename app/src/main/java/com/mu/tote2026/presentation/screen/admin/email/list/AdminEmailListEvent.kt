package com.mu.tote2026.presentation.screen.admin.email.list

sealed class AdminEmailListEvent {
    data class OnDelete(val docId: String) : AdminEmailListEvent()
}