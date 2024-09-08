package com.mu.tote2026.presentation.navigation.destination.admin.email

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.email.list.AdminEmailListScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_EMAIL_LIST_SCREEN

fun NavGraphBuilder.adminEmailList() {
    composable(ADMIN_EMAIL_LIST_SCREEN) {
        AdminEmailListScreen()
    }
}