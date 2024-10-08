package com.mu.tote2026.domain.usecase.game_usecase

import com.mu.tote2026.domain.repository.GameRepository

class GetGameList(
    private val gameRepository: GameRepository
) {
    operator fun invoke() = gameRepository.getGameList()
}