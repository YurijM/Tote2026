package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.StakeRoute

fun NavController.navigateToStake(
    args: StakeRoute
) {
    navigate(StakeRoute(args.gameId, args.gamblerId))
}