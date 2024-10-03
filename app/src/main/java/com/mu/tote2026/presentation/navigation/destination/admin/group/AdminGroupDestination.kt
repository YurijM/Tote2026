package com.mu.tote2026.presentation.navigation.destination.admin.group

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.group.AdminGroupScreen
import com.mu.tote2026.presentation.utils.KEY_ID
import com.mu.tote2026.presentation.utils.Route.ADMIN_GROUP_SCREEN

fun NavGraphBuilder.adminGroup(
    toAdminGroupList: () -> Unit
) {
    composable(
        "$ADMIN_GROUP_SCREEN/{$KEY_ID}"
    ) {
        AdminGroupScreen(
            toAdminGroupList = toAdminGroupList
        )
    }
}