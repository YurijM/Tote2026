package com.mu.tote2026.presentation.navigation.destination.stake

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.stake.StakeScreen
import com.mu.tote2026.presentation.utils.KEY_GAMBLER_ID
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.Route.STAKE_SCREEN

fun NavGraphBuilder.stake(
    toStakeList: () -> Unit
) {
    composable("$STAKE_SCREEN/{$KEY_ID}/{$KEY_GAMBLER_ID}") {
        StakeScreen(
            toStakeList = toStakeList
        )
    }
}