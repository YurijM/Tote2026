package com.mu.tote2026.presentation.navigation.destination.admin.email

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_EMAIL_LIST_SCREEN

fun NavController.navigateToAdminEmailList() {
    navigate(ADMIN_EMAIL_LIST_SCREEN) {
        popUpTo(ADMIN_EMAIL_LIST_SCREEN) {
            inclusive = true
        }
    }
}