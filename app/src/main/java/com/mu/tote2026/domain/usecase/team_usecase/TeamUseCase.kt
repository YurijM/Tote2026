package com.mu.tote2026.domain.usecase.team_usecase

data class TeamUseCase(
    val getTeamList: GetTeamList,
    val getTeam: GetTeam,
    val saveTeam: SaveTeam,
    val deleteTeam: DeleteTeam,
)
