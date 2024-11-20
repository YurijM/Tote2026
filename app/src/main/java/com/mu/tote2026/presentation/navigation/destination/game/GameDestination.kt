package com.mu.tote2026.presentation.navigation.destination.game

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.GameDestination
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination
import com.mu.tote2026.presentation.screen.game.GameScreen

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.game(
    toGroupGamesList: (GroupGamesDestination) -> Unit,
    toGameList: () -> Unit
) {
    composable<GameDestination> { backStackEntry ->
        GameScreen(
            toGroupGamesList = toGroupGamesList,
            toGameList = toGameList
        )
    }
}