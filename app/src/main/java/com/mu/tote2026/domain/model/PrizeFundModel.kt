package com.mu.tote2026.domain.model

data class PrizeFundModel(
    val prizeFund: Int = 0,
    val groupPrizeFund: Double = 0.0,
    val playoffPrizeFund: Double = 0.0,
    val winnersPrizeFund: Double = 0.0,
    val winnersPrizeFundByStake: Double = 0.0,
)
