package com.mu.tote2026.presentation.navigation.destination.game

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.game.list.GameListScreen
import com.mu.tote2026.presentation.screen.stake.list.StakeListScreen
import com.mu.tote2026.presentation.utils.Route.GAME_LIST_SCREEN
import com.mu.tote2026.presentation.utils.Route.STAKE_LIST_SCREEN

fun NavGraphBuilder.gameList() {
    composable(GAME_LIST_SCREEN) {
        GameListScreen()
    }
}