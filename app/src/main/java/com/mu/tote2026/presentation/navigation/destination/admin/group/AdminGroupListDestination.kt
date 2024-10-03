package com.mu.tote2026.presentation.navigation.destination.admin.group

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mu.tote2026.presentation.screen.admin.group.list.AdminGroupListScreen
import com.mu.tote2026.presentation.utils.Route.ADMIN_GROUP_LIST_SCREEN

fun NavGraphBuilder.adminGroupList(
    toGroupEdit: (String) -> Unit
) {
    composable(ADMIN_GROUP_LIST_SCREEN) {
        AdminGroupListScreen(
            toGroupEdit = toGroupEdit
        )
    }
}