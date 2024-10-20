package com.mu.tote2026.presentation.screen.stake.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.presentation.components.TeamFlag
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.asDateTime

@Composable
fun StakeListItem(
    game: GameModel,
    onEdit: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 4.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 12.dp
            )
            .clickable { onEdit() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                val group = if (game.groupId.toInt() <= GROUPS_COUNT)
                    stringResource(R.string.group_name, game.group)
                else
                    game.group
                Text(
                    text = stringResource(R.string.game_number, game.id) + " ($group)"
                )
                Text(
                    game.start.asDateTime()
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = game.team1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    TeamFlag(game.flag1)
                }
                Text(text = " - ")
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    TeamFlag(game.flag2)
                    Text(
                        text = game.team2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            if (game.stakes.isEmpty()) {
                Text(
                    text = stringResource(R.string.stake_is_absent),
                    fontWeight = FontWeight.Bold
                )
            } else {
                val stake = game.stakes[0]
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    Text(
                        text = stake.goal1
                    )
                    Text(text = ":")
                    Text(
                        text = stake.goal2
                    )
                    Playoff(stake)
                }
            }
        }
    }
}

@Composable
private fun Playoff(
    stake: StakeModel
) {
    if (stake.addGoal1.isNotBlank() && stake.addGoal2.isNotBlank()) {
        Text(
            text = stringResource(R.string.add_time_scope, stake.addGoal1, stake.addGoal2),
            lineHeight = .1.em
        )
    }
    if (stake.byPenalty.isNotBlank()) {
        Text(
            text = stringResource(R.string.winner_by_penalty, stake.byPenalty),
            lineHeight = .1.em
        )
    }
}