package com.mu.tote2026.domain.model

data class StakeModel(
    val gameId: String = "",
    val gamblerId: String = "",
    val goal1: String = "",
    val goal2: String = "",
    val addGoal1: String = "",
    val addGoal2: String = "",
    val byPenalty: String = "",
    val cashPrize: Int = 0,
)
