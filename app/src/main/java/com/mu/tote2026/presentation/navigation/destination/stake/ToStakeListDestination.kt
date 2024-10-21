package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.STAKE_LIST_SCREEN

fun NavController.navigateToStakeList() {
    navigate(STAKE_LIST_SCREEN) {
        popUpTo(STAKE_LIST_SCREEN) {
            inclusive = true
        }
    }
}