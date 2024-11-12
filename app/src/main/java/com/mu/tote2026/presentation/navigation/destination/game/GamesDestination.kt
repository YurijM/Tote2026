package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.GamesDestination
import com.mu.tote2026.presentation.screen.game.list.GameListScreen

fun NavGraphBuilder.games() {
    /*composable(GAME_LIST_SCREEN) {
        GameListScreen()
    }*/
    composable<GamesDestination> {
        GameListScreen()
    }
}