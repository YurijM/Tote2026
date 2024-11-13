package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.GamesDestination
import com.mu.tote2026.presentation.navigation.Destinations.StakesDestination

fun NavController.navigateToGames() {
    navigate(GamesDestination) {
        popUpTo(StakesDestination) {
            inclusive = true
        }
    }
}