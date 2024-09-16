package com.mu.tote2026.presentation.navigation.destination.admin.gambler

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.gambler.list.AdminGamblerListScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_GAMBLER_LIST_SCREEN

fun NavGraphBuilder.adminGamblerList(
    toGamblerEdit: (String) -> Unit
) {
    composable(ADMIN_GAMBLER_LIST_SCREEN) {
        AdminGamblerListScreen(
            toGamblerEdit = toGamblerEdit
        )
    }
}