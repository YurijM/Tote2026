package com.mu.tote2026.presentation.navigation.destination.admin.team

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_TEAM_LIST_SCREEN

fun NavController.navigateToAdminTeamList() {
    navigate(ADMIN_TEAM_LIST_SCREEN) {
        popUpTo(ADMIN_TEAM_LIST_SCREEN) {
            inclusive = true
        }
    }
}
