package com.mu.tote2026.domain.model

data class GamblerModel(
    val id: String = "",
    val email: String = "",
    val nickname: String = "",
    val photoUrl: String = "",
    val gender: String = "",
    @field:JvmField
    val isAdmin: Boolean = false,
    val rate: Int = 0,
    val ratePercent: Double = 0.0,
    val pointsPrev: Double = 0.0,
    val points: Double = 0.0,
    val placePrev: Int = 0,
    val place: Int = 0,
    val cashPrize: Double = 0.0
)
