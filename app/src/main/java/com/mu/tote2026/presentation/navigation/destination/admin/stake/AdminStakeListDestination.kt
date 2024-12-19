package com.mu.tote2026.presentation.navigation.destination.admin.stake

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.stake.AdminStakeListScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_STAKE_LIST_SCREEN

fun NavGraphBuilder.adminStakeList() {
    composable(ADMIN_STAKE_LIST_SCREEN) {
        AdminStakeListScreen()
    }
}