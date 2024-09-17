package com.mu.tote2026.presentation.navigation.destination.admin.gambler

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.gambler.AdminGamblerScreen
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.Route.ADMIN_GAMBLER_SCREEN

fun NavGraphBuilder.adminGambler(
    toAdminGamblerList: () -> Unit
) {
    composable(
        "$ADMIN_GAMBLER_SCREEN/{$KEY_ID}"
    ) {
        AdminGamblerScreen (
            toAdminGamblerList = toAdminGamblerList
        )
    }
}