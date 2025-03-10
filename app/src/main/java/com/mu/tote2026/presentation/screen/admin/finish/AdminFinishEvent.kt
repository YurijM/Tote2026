package com.mu.tote2026.presentation.screen.admin.finish

sealed class AdminFinishEvent {
    data class OnFinishedChange(val finished: Boolean) : AdminFinishEvent()
    data class OnChampionChange(val champion: String) : AdminFinishEvent()
    data object OnSave : AdminFinishEvent()
}