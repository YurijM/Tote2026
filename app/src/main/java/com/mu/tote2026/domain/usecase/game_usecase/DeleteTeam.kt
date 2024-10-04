package com.mu.tote2026.domain.usecase.game_usecase

import com.mu.tote2026.domain.repository.GameRepository

class DeleteTeam(
    private val gameRepository: GameRepository
) {
    operator fun invoke(id: String) = gameRepository.deleteTeam(id)
}