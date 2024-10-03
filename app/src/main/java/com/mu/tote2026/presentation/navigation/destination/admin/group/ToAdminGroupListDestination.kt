package com.mu.tote2026.presentation.navigation.destination.admin.group

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_GROUP_LIST_SCREEN

fun NavController.navigateToAdminGroupList() {
    navigate(ADMIN_GROUP_LIST_SCREEN) {
        popUpTo(ADMIN_GROUP_LIST_SCREEN) {
            inclusive = true
        }
    }
}