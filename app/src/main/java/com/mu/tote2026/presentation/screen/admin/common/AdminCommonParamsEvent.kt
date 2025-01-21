package com.mu.tote2026.presentation.screen.admin.common

sealed class AdminCommonParamsEvent {
    data class OnPrizeFundChange(val prizeFund: String) : AdminCommonParamsEvent()
    data object OnSave : AdminCommonParamsEvent()
}
