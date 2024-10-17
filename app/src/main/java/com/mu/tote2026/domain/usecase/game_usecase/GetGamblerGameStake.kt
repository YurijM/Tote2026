package com.mu.tote2026.domain.usecase.game_usecase

import com.mu.tote2026.domain.repository.GameRepository

class GetGamblerGameStake(
    private val gameRepository: GameRepository
) {
    operator fun invoke(gameId: String, gamblerId: String) = gameRepository.getGamblerGameStake(gameId, gamblerId)
}