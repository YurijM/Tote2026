package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.StakeRoute
import com.mu.tote2026.presentation.navigation.Destinations.StakesRoute

fun NavController.navigateToStakeList() {
    navigate(StakesRoute) {
        popUpTo(StakeRoute) {
            inclusive = true
        }
    }
}