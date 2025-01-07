package com.mu.tote2026.domain.model

data class StakeModel(
    val gameId: String = "",
    val gamblerId: String = "",
    val gamblerNickname: String = "",
    val gamblerRatePercent: Double = 0.0,
    val goal1: String = "",
    val goal2: String = "",
    val addGoal1: String = "",
    val addGoal2: String = "",
    val result: String = "",
    val addResult: String = "",
    val byPenalty: String = "",
    val points: Double = -1.0,
    val addPoints: Double = 0.0,
    val cashPrize: Int = 0,
)
