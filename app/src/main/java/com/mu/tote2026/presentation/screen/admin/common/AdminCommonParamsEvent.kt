package com.mu.tote2026.presentation.screen.admin.common

sealed class AdminCommonParamsEvent {
    data class OnPrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data class OnGroupPrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data class OnPlayoffPrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data class OnWinnersPrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data class OnWinnersPrizeFundByStakeChange(val money: String) : AdminCommonParamsEvent()
    data class OnPlace1PrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data class OnPlace2PrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data class OnPlace3PrizeFundChange(val money: String) : AdminCommonParamsEvent()
    data object OnSave : AdminCommonParamsEvent()
}
