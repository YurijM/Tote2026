package com.mu.tote2026.presentation.navigation.destination.admin.group

import androidx.navigation.NavController
import com.mu.tote2026.presentation.utils.Route.ADMIN_GROUP_LIST_SCREEN
import com.mu.tote2026.presentation.utils.Route.ADMIN_GROUP_SCREEN

fun NavController.navigateToAdminGroup(id: String) {
    navigate("$ADMIN_GROUP_SCREEN/$id") {
        popUpTo(ADMIN_GROUP_LIST_SCREEN) {
            //inclusive = true
        }
    }
}