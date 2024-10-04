package com.mu.tote2026.domain.usecase.game_usecase

import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.domain.repository.GameRepository

class SaveTeam(
    private val gameRepository: GameRepository
) {
    operator fun invoke(team: TeamModel) = gameRepository.saveTeam(team)
}