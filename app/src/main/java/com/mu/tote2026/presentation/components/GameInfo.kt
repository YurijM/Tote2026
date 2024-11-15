package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.asDateTime


@Composable
fun GameInfo(
    game: GameModel
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        text = if (game.start.isNotBlank()) game.start.asDateTime() else game.start,
        textAlign = TextAlign.Center,
        lineHeight = .1.em
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.game_number, game.id)
        )
        Text(
            modifier = Modifier.weight(1f),
            text = if (game.groupId.isNotBlank() && game.groupId.toInt() <= GROUPS_COUNT)
                stringResource(R.string.group_name, game.group)
            else game.group,
            textAlign = TextAlign.End
        )
    }
}
