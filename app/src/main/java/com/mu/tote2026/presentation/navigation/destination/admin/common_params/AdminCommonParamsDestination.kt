package com.mu.tote2026.presentation.navigation.destination.admin.common_params

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.common.AdminCommonParamsScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_COMMON_PARAMS_SCREEN

fun NavGraphBuilder.adminCommonParams() {
    composable(ADMIN_COMMON_PARAMS_SCREEN) {
        AdminCommonParamsScreen()
    }
}