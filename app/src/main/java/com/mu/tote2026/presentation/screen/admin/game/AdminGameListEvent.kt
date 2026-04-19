package com.mu.tote2026.presentation.screen.admin.game

import android.content.Context

sealed class AdminGameListEvent {
    data class OnUnload(val context: Context) : AdminGameListEvent()
    data class OnLoad(val context: Context) : AdminGameListEvent()
}