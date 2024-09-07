package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.stake.list.StakeListScreen
import com.mu.tote2026.presentation.utils.Route.STAKE_LIST_SCREEN

fun NavGraphBuilder.stakeList() {
    composable(STAKE_LIST_SCREEN) {
        StakeListScreen()
    }
}