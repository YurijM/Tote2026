package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mu.tote2026.R
import com.mu.tote2026.presentation.utils.GROUPS

@Composable
fun GameIdAndGroup(
    gameId: String,
    error: String,
    group: String,
    onChange: (String) -> Unit,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AppOutlinedTextField(
                modifier = Modifier
                    .width(108.dp)
                    .padding(end = 8.dp),
                label = stringResource(R.string.game_id),
                textAlign = TextAlign.Center,
                value = gameId,
                onChange = { newValue -> onChange(newValue) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                )
            )
            AppDropDownList(
                list = GROUPS,
                label = stringResource(R.string.group),
                selectedItem = group,
                onClick = { selectedItem -> onClick(selectedItem) }
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