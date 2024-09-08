package com.mu.tote2026.presentation.navigation.destination.admin.game

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.game.AdminGameListScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_GAME_LIST_SCREEN

fun NavGraphBuilder.adminGameList() {
    composable(ADMIN_GAME_LIST_SCREEN) {
        AdminGameListScreen()
    }
}