package com.mu.tote2026.presentation.screen.admin.gambler

sealed class AdminGamblerEvent {
    data class OnRateChange(val rate: String) : AdminGamblerEvent()
    data class OnIsAdminChange(val isAdmin: Boolean) : AdminGamblerEvent()
    data object OnSave: AdminGamblerEvent()
}