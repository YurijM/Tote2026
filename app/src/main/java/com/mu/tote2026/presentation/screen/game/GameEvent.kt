package com.mu.tote2026.presentation.screen.game

sealed class GameEvent {
    data class OnStartChange(val start: String) : GameEvent()
    data class OnGameIdChange(val id: String) : GameEvent()
    data class OnGroupChange(val group: String) : GameEvent()
    data class OnTeamChange(val teamNo: Int, val team: String) : GameEvent()
    data class OnGoalChange(val extraTime: Boolean, val teamNo: Int, val goal: String) : GameEvent()
    data class OnByPenaltyChange(val team: String) : GameEvent()
    object OnSave :GameEvent()
    object OnGenerateGame :GameEvent()
}