package com.mu.tote2026.presentation.screen.admin.group

sealed class AdminGroupEvent {
    data class OnIdChange(val id: String) : AdminGroupEvent()
    data class OnGroupChange(val group: String) : AdminGroupEvent()
    data object OnSave : AdminGroupEvent()
}