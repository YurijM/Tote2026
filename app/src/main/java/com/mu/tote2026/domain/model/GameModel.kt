package com.mu.tote2026.domain.model

data class GameModel(
    val id: String = "",
    val start: String = "",
    val groupId: String = "",
    val group: String = "",
    val team1: String = "",
    val team1ItemNo: String = "",
    val flag1: String = "",
    val team2: String = "",
    val team2ItemNo: String = "",
    val flag2: String = "",
    val goal1: String = "",
    val goal2: String = "",
    val addGoal1: String = "",
    val addGoal2: String = "",
    //@field:JvmField
    val byPenalty: String = "",
    val winCount: Int = 0,
    val drawCount: Int = 0,
    val defeatCount: Int = 0,
    val stakes: List<StakeModel> = listOf()
)
