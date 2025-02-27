package com.mu.tote2026.presentation.navigation.destination.admin.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.prize_fund.AdminPrizeFundScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_PRIZE_FUND_SCREEN

fun NavGraphBuilder.adminPrizeFund() {
    composable(ADMIN_PRIZE_FUND_SCREEN) {
        AdminPrizeFundScreen()
    }
}