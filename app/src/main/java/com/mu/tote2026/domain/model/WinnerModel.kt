package com.mu.tote2026.domain.model

data class WinnerModel(
    val gamblerId: String = "",
    val gamblerNickname: String = "",
    val cashPrize: Double = 0.0,
    val cashPrizeBStake: Double = 0.0
)
