package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavController
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination

fun NavController.navigateToGroupGames(
    args: GroupGamesDestination
) {
    navigate(GroupGamesDestination(args.group)) {
        popUpTo(GroupGamesDestination(args.group)) {
            inclusive = true
        }
    }
}