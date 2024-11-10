package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.GamesRoute
import com.mu.tote2026.presentation.screen.game.list.GameListScreen
import com.mu.tote2026.presentation.utils.Route.GAME_LIST_SCREEN

fun NavGraphBuilder.games() {
    /*composable(GAME_LIST_SCREEN) {
        GameListScreen()
    }*/
    composable<GamesRoute> {
        GameListScreen()
    }
}