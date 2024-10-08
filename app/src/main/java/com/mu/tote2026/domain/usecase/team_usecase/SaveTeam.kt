package com.mu.tote2026.domain.usecase.team_usecase

import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.domain.repository.TeamRepository

class SaveTeam(
    private val teamRepository: TeamRepository
) {
    operator fun invoke(team: TeamModel) = teamRepository.saveTeam(team)
}