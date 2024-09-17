package com.mu.tote2026.presentation.navigation.destination.admin.gambler

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_GAMBLER_LIST_SCREEN

fun NavController.navigateToAdminGamblerList() {
    navigate(ADMIN_GAMBLER_LIST_SCREEN) {
        popUpTo(ADMIN_GAMBLER_LIST_SCREEN) {
            inclusive = true
        }
    }
}