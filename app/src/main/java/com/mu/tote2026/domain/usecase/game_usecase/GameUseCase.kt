package com.mu.tote2026.domain.usecase.game_usecase

data class GameUseCase(
    val getTeamList: GetTeamList,
    val getTeam: GetTeam,
    val saveTeam: SaveTeam,
    val deleteTeam: DeleteTeam,

    val getGameList: GetGameList,
    val getGame: GetGame,
    val saveGame: SaveGame,
    val deleteGame: DeleteGame,
)
