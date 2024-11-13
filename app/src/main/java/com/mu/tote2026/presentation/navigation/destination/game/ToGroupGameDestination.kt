package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination

fun NavController.navigateToGroupGame(
    args: GroupGamesDestination
) {
    navigate(GroupGamesDestination(args.group))
}