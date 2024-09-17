package com.mu.tote2026.presentation.navigation.destination.admin.gambler

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_GAMBLER_LIST_SCREEN
import com.mu.tote2026.presentation.utils.Route.ADMIN_GAMBLER_SCREEN

fun NavController.navigateToAdminGambler(id: String) {
    navigate("$ADMIN_GAMBLER_SCREEN/$id") {
        popUpTo(ADMIN_GAMBLER_LIST_SCREEN) {
            //inclusive = true
        }
    }}