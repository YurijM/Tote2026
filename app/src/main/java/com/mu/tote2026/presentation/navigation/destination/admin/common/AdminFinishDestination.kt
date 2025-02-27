package com.mu.tote2026.presentation.navigation.destination.admin.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.finish.AdminFinishScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_FINISH_SCREEN

fun NavGraphBuilder.adminFinish(
    toAdmin: () -> Unit
) {
    composable(ADMIN_FINISH_SCREEN) {
        AdminFinishScreen(
            toAdmin = toAdmin
        )
    }
}