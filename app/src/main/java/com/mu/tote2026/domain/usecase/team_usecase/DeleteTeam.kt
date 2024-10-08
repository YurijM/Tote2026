package com.mu.tote2026.domain.usecase.team_usecase

import com.mu.tote2026.domain.repository.TeamRepository

class DeleteTeam(
    private val teamRepository: TeamRepository
) {
    operator fun invoke(id: String) = teamRepository.deleteTeam(id)
}