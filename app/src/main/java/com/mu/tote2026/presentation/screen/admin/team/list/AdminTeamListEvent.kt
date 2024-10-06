package com.mu.tote2026.presentation.screen.admin.team.list

sealed class AdminTeamListEvent {
    data object OnLoad : AdminTeamListEvent()
}