package com.mu.tote2026.domain.model

data class Gambler(
    val email: String = "",
    val nickname: String = "",
    val photoUrl: String = "",
    val gender: String = "",
    val additionally: AdditionallyForGambler = AdditionallyForGambler()
)
