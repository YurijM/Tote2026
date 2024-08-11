package com.mu.tote2026.domain.model

data class AdditionallyForGambler(
    val rate: Int = 0,
    val isAdmin: Boolean = false,
    val pointsPrev: Double = 0.00,
    val points: Double = 0.00,
    val placePrev: Int = 0,
    val place: Int = 0
)
