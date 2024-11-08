package com.mu.tote2026.domain.usecase.game_usecase

data class GameUseCase(
    val getGameList: GetGameList,
    val getGame: GetGame,
    val saveGame: SaveGame,
    val deleteGame: DeleteGame,

    val getGroupTeamResult: GetGroupTeamResult,

    val getGamblerStakes: GetGamblerStakes,
    val getGamblerGameStake: GetGamblerGameStake,
    val saveStake: SaveStake,
)
