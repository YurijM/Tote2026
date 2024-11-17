package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.StakeModel

@Composable
fun ExtraTime(
    stake: StakeModel,
    errorMessage: String,
    onAddGoal1Change: (String) -> Unit,
    onAddGoal2Change: (String) -> Unit,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.add_time),
        textAlign = TextAlign.Center
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            AppOutlinedTextField(
                value = stake.addGoal1,
                textAlign = TextAlign.Center,
                onChange = { newValue -> onAddGoal1Change(newValue) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                ),
                modifier = Modifier.width(dimensionResource(R.dimen.goal_edit_width))
            )
        }
        Text(
            text = " : ",
        )
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            AppOutlinedTextField(
                value = stake.addGoal2,
                textAlign = TextAlign.Center,
                onChange = { newValue -> onAddGoal2Change(newValue) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                ),
                modifier = Modifier.width(dimensionResource(R.dimen.goal_edit_width))
            )
        }
    }
    if (errorMessage.isNotBlank()) {
        TextError(
            error = errorMessage,
            textAlign = TextAlign.Center
        )
    }
}
