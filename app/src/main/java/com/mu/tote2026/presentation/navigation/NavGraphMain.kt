package com.mu.tote2026.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mu.tote2026.presentation.navigation.destination.game.gameList
import com.mu.tote2026.presentation.navigation.destination.prognosis.prognosis
import com.mu.tote2026.presentation.navigation.destination.rating.rating
import com.mu.tote2026.presentation.navigation.destination.stake.stakeList
import com.mu.tote2026.presentation.utils.Route.RATING_SCREEN

@Composable
fun NavGraphMain(
    navMainController: NavHostController
) {
    NavHost (
        navController = navMainController,
        startDestination = RATING_SCREEN
    ) {
        rating()
        stakeList()
        prognosis()
        gameList()
    }
}