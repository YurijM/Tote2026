package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mu.tote2026.R

@Composable
fun ByPenalty(
    teams: List<String>,
    selectedTeam: String,
    errorMessage: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AppDropDownList(
            list = teams,
            label = stringResource(R.string.by_penalty),
            selectedItem = selectedTeam,
            onClick = { selectedItem -> onClick(selectedItem) }
        )
    }
    if (errorMessage.isNotBlank()) {
        TextError(
            error = errorMessage,
            textAlign = TextAlign.Center
        )
    }
}