package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.GamesDestination
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination
import com.mu.tote2026.presentation.screen.game.list.GameListScreen

fun NavGraphBuilder.games(
    toGroupGameList: (GroupGamesDestination) -> Unit
) {
    composable<GamesDestination> {
        GameListScreen(
            toGroupGameList = toGroupGameList
        )
    }
}