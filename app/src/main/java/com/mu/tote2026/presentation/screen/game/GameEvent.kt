package com.mu.tote2026.presentation.screen.game

sealed class GameEvent {
    data class OnGoalChange(val extraTime: Boolean, val teamNo: Int, val goal: String) : GameEvent()
    data class OnByPenaltyChange(val team: String) : GameEvent()
    object OnSave :GameEvent()
    object OnGenerateStake :GameEvent()
}