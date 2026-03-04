package com.mu.tote2026.domain.usecase.team_usecase

import android.net.Uri
import com.mu.tote2026.domain.repository.TeamRepository

class SaveTeamFlag(
    private val teamRepository: TeamRepository
) {
    operator fun invoke(id: String, uri: Uri) =
        teamRepository.saveTeamFlag(id, uri)
}
