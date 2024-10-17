package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.STAKE_LIST_SCREEN
import com.mu.tote2026.presentation.utils.Route.STAKE_SCREEN

fun NavController.navigateToStake(
    gameId: String,
    gamblerId: String
) {
    navigate("$STAKE_SCREEN/$gameId/$gamblerId") {
        popUpTo(STAKE_LIST_SCREEN) {
            //inclusive = true
        }
    }
}