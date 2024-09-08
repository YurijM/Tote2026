package com.mu.tote2026.presentation.screen.admin.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mu.tote2026.presentation.utils.AdminNavItem

@Composable
fun AdminMainScreen(
    toItem: (String) -> Unit
) {
    val navItems = mutableListOf(
        AdminNavItem.AdminEmailItem,
        AdminNavItem.AdminGamblerItem,
        AdminNavItem.AdminTeamItem,
        AdminNavItem.AdminGameItem,
        AdminNavItem.AdminStakeItem,
        AdminNavItem.AdminFinishItem,
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 8.dp,
                horizontal = 48.dp
            )
    ) {
        items(navItems) { item ->
            AdminMainItemScreen(item) { route ->
                toItem(route)
            }
        }
    }
}