package com.mu.tote2026.presentation.screen.admin.team.list

import com.mu.tote2026.domain.model.TeamModel

sealed class AdminTeamListEvent {
    data object OnLoad : AdminTeamListEvent()
    data class OnDelete(val team: TeamModel) : AdminTeamListEvent()
}