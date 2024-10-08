package com.mu.tote2026.presentation.screen.admin.game

sealed class AdminGameListEvent {
    data object OnLoad : AdminGameListEvent()
}