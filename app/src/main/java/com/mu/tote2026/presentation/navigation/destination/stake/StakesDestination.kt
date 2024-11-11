package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.StakeRoute
import com.mu.tote2026.presentation.navigation.Destinations.StakesRoute
import com.mu.tote2026.presentation.screen.stake.list.StakeListScreen

fun NavGraphBuilder.stakes(
    toStakeEdit: (StakeRoute) -> Unit
) {
    composable<StakesRoute> {
        StakeListScreen(
            toStakeEdit = toStakeEdit
        )
    }
}