package com.mu.tote2026.domain.usecase.gambler_usecase

import com.mu.tote2026.domain.usecase.game_usecase.DeleteTeam
import com.mu.tote2026.domain.usecase.game_usecase.GetTeam
import com.mu.tote2026.domain.usecase.game_usecase.GetTeamList
import com.mu.tote2026.domain.usecase.game_usecase.SaveTeam

data class GameUseCase(
    val getTeamList: GetTeamList,
    val getTeam: GetTeam,
    val saveTeam: SaveTeam,
    val deleteTeam: DeleteTeam
)
