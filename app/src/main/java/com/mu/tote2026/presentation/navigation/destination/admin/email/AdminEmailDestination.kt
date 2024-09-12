package com.mu.tote2026.presentation.navigation.destination.admin.email

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.email.AdminEmailScreen
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.Route.ADMIN_EMAIL_SCREEN

fun NavGraphBuilder.adminEmail(
    toAdminEmailList: () -> Unit
) {
    composable(
        "$ADMIN_EMAIL_SCREEN/{$KEY_ID}"
    ) {
        AdminEmailScreen(
            toAdminEmailList = toAdminEmailList
        )
    }
}