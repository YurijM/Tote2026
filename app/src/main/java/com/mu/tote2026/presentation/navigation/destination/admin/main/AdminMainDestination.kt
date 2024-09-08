package com.mu.tote2026.presentation.navigation.destination.admin.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.main.AdminMainScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_MAIN_SCREEN

fun NavGraphBuilder.adminMain(
    toItem: (String) -> Unit
) {
    composable(ADMIN_MAIN_SCREEN) {
        AdminMainScreen(
            toItem = toItem
        )
    }
}