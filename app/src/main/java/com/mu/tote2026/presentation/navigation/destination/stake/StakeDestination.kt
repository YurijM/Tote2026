package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.StakeDestination
import com.mu.tote2026.presentation.screen.stake.StakeScreen

fun NavGraphBuilder.stake(
    toStakeList: () -> Unit
) {
    composable<StakeDestination> { backStackEntry ->
        StakeScreen(
            toStakeList = toStakeList
        )
    }
}