package com.mu.tote2026.presentation.navigation.destination.admin.email

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_EMAIL_LIST_SCREEN
import com.mu.tote2026.presentation.utils.Route.ADMIN_EMAIL_SCREEN

fun NavController.navigateToAdminEmail(id: String) {
    navigate("$ADMIN_EMAIL_SCREEN/$id") {
        popUpTo(ADMIN_EMAIL_LIST_SCREEN) {
            //inclusive = true
        }
    }
}