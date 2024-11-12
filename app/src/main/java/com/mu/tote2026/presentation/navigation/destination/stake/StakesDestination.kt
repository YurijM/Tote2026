package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.navigation.Destinations.StakeDestination
import com.mu.tote2026.presentation.navigation.Destinations.StakesDestination
import com.mu.tote2026.presentation.screen.stake.list.StakeListScreen

fun NavGraphBuilder.stakes(
    toStakeEdit: (StakeDestination) -> Unit
) {
    composable<StakesDestination> {
        StakeListScreen(
            toStakeEdit = toStakeEdit
        )
    }
}