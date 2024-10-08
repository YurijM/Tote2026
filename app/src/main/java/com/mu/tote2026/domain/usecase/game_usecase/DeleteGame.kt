package com.mu.tote2026.domain.usecase.game_usecase

import com.mu.tote2026.domain.repository.GameRepository

class DeleteGame(
    private val gameRepository: GameRepository
) {
    operator fun invoke(id: String) = gameRepository.deleteGame(id)
}