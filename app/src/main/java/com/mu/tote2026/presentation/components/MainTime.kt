package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel

@Composable
fun MainTime(
    game: GameModel,
    stake: StakeModel? = null,
    errorMessage: String,
    onGoal1Change: (String) -> Unit,
    onGoal2Change: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = game.team1,
            textAlign = TextAlign.End,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
        )
        TeamFlag(game.flag1, 20.dp)
        Spacer(modifier = Modifier.width(12.dp))
        TeamFlag(game.flag2, 20.dp)
        Text(
            text = game.team2,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ) {
            AppOutlinedTextField(
                value = if (stake != null) stake.goal1 else game.goal1,
                textAlign = TextAlign.Center,
                onChange = { newValue -> onGoal1Change(newValue) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                ),
                modifier = Modifier.width(dimensionResource(R.dimen.goal_edit_width))
            )
        }
        Text(text = " : ")
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            AppOutlinedTextField(
                value = if (stake != null) stake.goal2 else game.goal2,
                textAlign = TextAlign.Center,
                onChange = { newValue -> onGoal2Change(newValue) },
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
