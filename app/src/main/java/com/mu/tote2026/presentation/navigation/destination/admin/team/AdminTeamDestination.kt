package com.mu.tote2026.presentation.navigation.destination.admin.team

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.team.AdminTeamScreen
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.Route.ADMIN_TEAM_SCREEN

fun NavGraphBuilder.adminTeam(
    toAdminTeamList: () -> Unit
) {
    composable(
        "$ADMIN_TEAM_SCREEN/{$KEY_ID}"
    ) {
        AdminTeamScreen(
            toAdminTeamList = toAdminTeamList
        )
    }
}