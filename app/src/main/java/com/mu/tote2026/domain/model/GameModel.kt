package com.mu.tote2026.domain.model

data class GameModel(
    val id: String = "",
    val start: String = "",
    val groupId: String = "",
    val group: String = "",
    val team1: String = "",
    val flag1: String = "",
    val team2: String = "",
    val flag2: String = "",
    val goal1: String = "",
    val goal2: String = "",
    val addGoal1: String = "",
    val addGoal2: String = "",
    @field:JvmField
    val byPenalty: String = "",
    val stakes: List<StakeModel> = listOf()
)
