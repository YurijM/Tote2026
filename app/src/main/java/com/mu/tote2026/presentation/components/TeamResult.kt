package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mu.tote2026.R

@Composable
fun TeamResult(
    teams: List<String>,
    team: String,
    goal: String,
    error: String,
    onTeamSelect: (String) -> Unit,
    onGoalSet: (String) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            AppDropDownList(
                list = teams,
                label = stringResource(R.string.team),
                selectedItem = team,
                onClick = { item -> onTeamSelect(item) }
            )
            AppOutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(52.dp),
                label = "",
                textAlign = TextAlign.Center,
                value = goal,
                onChange = { newValue -> onGoalSet(newValue) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                )
            )
        }
        if (error.isNotBlank()) {
            TextError(
                error = error,
                textAlign = TextAlign.Center
            )
        }
    }
}