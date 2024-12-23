package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.GameDestination

fun NavController.navigateToGame(
    args: GameDestination
) {
    navigate(GameDestination(args.id))
}