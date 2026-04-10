package com.mu.tote2026.presentation.navigation.destination.admin.game

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_GAME_LIST_SCREEN

fun NavController.navigateToAdminGames() {
    navigate(ADMIN_GAME_LIST_SCREEN) {
        popUpTo(ADMIN_GAME_LIST_SCREEN) {
            inclusive = true
        }
    }
}