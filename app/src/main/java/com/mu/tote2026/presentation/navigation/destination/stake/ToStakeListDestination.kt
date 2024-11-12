package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.StakesDestination

fun NavController.navigateToStakeList() {
    navigate(StakesDestination) {
        /*popUpTo(StakeDestination) {
            inclusive = true
        }*/
    }
}