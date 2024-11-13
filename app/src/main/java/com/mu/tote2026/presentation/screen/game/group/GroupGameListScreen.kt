package com.mu.tote2026.presentation.screen.game.group

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GroupGameListScreen(
    toGameList: () -> Unit
) {
    val viewModel: GroupGameListViewModel = hiltViewModel()

    Text("Group Game List Screen")
}