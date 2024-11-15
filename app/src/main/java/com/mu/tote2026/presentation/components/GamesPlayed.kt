package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GameModel

@Composable
fun GamesPlayed(
    game: GameModel,
    team1: String,
    team2: String
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End,
                    text = "${game.team1} ",
                    fontWeight = if (game.team1 in listOf(team1, team2))
                        FontWeight.Bold
                    else
                        FontWeight.Normal
                )
                TeamFlag(game.flag1)
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = "${game.goal1}:${game.goal2}"
                )
                TeamFlag(game.flag2)
                Text(
                    modifier = Modifier.weight(1f),
                    text = " ${game.team2}",
                    fontWeight = if (game.team2 in listOf(team1, team2))
                        FontWeight.Bold
                    else
                        FontWeight.Normal
                )
            }
            if (game.addGoal1.isNotBlank()) {
                Text(
                    text = "${game.goal1}:${game.goal2}" +
                            if (game.addGoal1.isNotBlank())
                                ", ${stringResource(id = R.string.add_time_score)} ${game.addGoal1}:${game.addGoal2}" +
                                        if (game.byPenalty.isNotBlank())
                                            ", ${stringResource(id = R.string.by_penalty)} ${game.byPenalty}"
                                        else ""
                            else ""
                )
            }
        }
    }
}
