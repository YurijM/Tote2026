package com.mu.tote2026.domain.usecase.team_usecase

import com.mu.tote2026.domain.repository.TeamRepository

class GetTeamList(
    private val teamRepository: TeamRepository
) {
    operator fun invoke() = teamRepository.getTeamList()
}