package com.mu.tote2026.domain.usecase.gambler_usecase

data class GamblerUseCase(
    val getGamblerList: GetGamblerList,
    val getGambler: GetGambler,
    val saveGambler: SaveGambler,
    val saveGamblerPhoto: SaveGamblerPhoto,

    val getCommonParams: GetCommonParams,
    val saveCommonParams: SaveCommonParams,

    val getWinners: GetWinners,
    val saveWinner: SaveWinner,
    val deleteWinners: DeleteWinners,
)
