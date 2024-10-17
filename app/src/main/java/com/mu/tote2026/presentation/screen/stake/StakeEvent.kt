package com.mu.tote2026.presentation.screen.stake

sealed class StakeEvent {
    data class OnGoal1Change(val goal: String) : StakeEvent()
    data class OnGoal2Change(val goal: String) : StakeEvent()
    data class OnAddGoal1Change(val goal: String) : StakeEvent()
    data class OnAddGoal2Change(val goal: String) : StakeEvent()
    data class OnByPenaltyChange(val team: String) : StakeEvent()
}