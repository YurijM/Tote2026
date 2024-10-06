package com.mu.tote2026.presentation.navigation.destination.admin.team

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.team.list.AdminTeamListScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_TEAM_LIST_SCREEN

fun NavGraphBuilder.adminTeamList() {
    composable(ADMIN_TEAM_LIST_SCREEN) {
        AdminTeamListScreen()
    }
}