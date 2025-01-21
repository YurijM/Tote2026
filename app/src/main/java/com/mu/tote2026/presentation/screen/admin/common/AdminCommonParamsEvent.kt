package com.mu.tote2026.presentation.screen.admin.common

sealed class AdminCommonParamsEvent {
    data class OnPrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data class OnGroupPrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data class OnPlayoffPrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data object OnSave : AdminCommonParamsEvent()
}
