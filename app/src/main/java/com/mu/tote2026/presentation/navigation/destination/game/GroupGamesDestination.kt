package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.GameDestination
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination
import com.mu.tote2026.presentation.screen.game.group.GroupGameListScreen

fun NavGraphBuilder.groupGames(
    toGameEdit: (GameDestination) -> Unit
) {
    composable<GroupGamesDestination>() { backStackEntry ->
        GroupGameListScreen(
            toGameEdit = toGameEdit
        )
    }
}