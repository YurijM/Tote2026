package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.StakeDestination

fun NavController.navigateToStake(
    args: StakeDestination
) {
    navigate(StakeDestination(args.gameId, args.gamblerId))
}