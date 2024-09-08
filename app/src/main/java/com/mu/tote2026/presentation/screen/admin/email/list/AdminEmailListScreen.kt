package com.mu.tote2026.presentation.screen.admin.email.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AdminEmailListScreen(
    viewModel: AdminEmailListViewModel = hiltViewModel(),
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Admin Email List Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}