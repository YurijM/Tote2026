package com.mu.tote2026.presentation.navigation.destination.admin.team

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_TEAM_LIST_SCREEN
import com.mu.tote2026.presentation.utils.Route.ADMIN_TEAM_SCREEN

fun NavController.navigateToAdminTeam(id: String) {
    navigate("$ADMIN_TEAM_SCREEN/$id") {
        popUpTo(ADMIN_TEAM_LIST_SCREEN) {
            //inclusive = true
        }
    }
}