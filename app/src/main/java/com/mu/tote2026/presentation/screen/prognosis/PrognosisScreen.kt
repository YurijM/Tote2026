package com.mu.tote2026.presentation.screen.prognosis

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.asDateTime
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun PrognosisScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: PrognosisViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result
    var games by remember { mutableStateOf<List<GameModel>>(listOf()) }

    LaunchedEffect(key1 = result) {
        toLog("PrognosisScreen $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
                games = result.data
            }

            is UiState.Error -> {
                isLoading = false
                error = result.error
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Title(stringResource(R.string.prognosis))
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            items(games) { game ->
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    CardTitle(game)
                    GameResult(game)

                    game.stakes.forEach { stake ->
                        GamblerStake(stake)
                    }
                }
            }
        }
    }
}
@Composable
private fun CardTitle(game: GameModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 4.dp,
                start = 8.dp,
                end = 8.dp,
            )
    ) {
        Text(
            text = game.start.asDateTime(),
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "№${game.id}",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = (if (game.groupId.toInt() < GROUPS_COUNT) "группа " else "") + game.group,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.weight(1f),
        )
    }
}
@Composable
private fun GameResult(game: GameModel) {
    Text(
        text = "${game.team1} - ${game.team2}",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
    val result = "${game.goal1} : ${game.goal2}" +
            (if (game.addResult.isNotBlank()) {
                stringResource(R.string.add_time_result, game.addGoal1, game.addGoal2)
            } else "") +
            if (game.byPenalty.isNotBlank()) {
                "\n${stringResource(R.string.by_penalty_result, game.byPenalty)}"
            } else ""
    Text(
        text = result,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
@SuppressLint("DefaultLocale")
@Composable
private fun GamblerStake(stake: StakeModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                bottom = 4.dp,
            )
    ) {
        Text(
            text = stake.gamblerId,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        val result = "${stake.goal1} : ${stake.goal2}" +
                (if (stake.addResult.isNotBlank()) {
                    stringResource(R.string.add_time_result, stake.addGoal1, stake.addGoal2)
                } else "") +
                if (stake.byPenalty.isNotBlank()) {
                    "\n${stringResource(R.string.by_penalty_result, stake.byPenalty)}"
                } else ""
        Text(
            text = result,
            textAlign = TextAlign.Center,
            maxLines = 3,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = String.format("%.2f", stake.points),
            textAlign = TextAlign.End,
            maxLines = 1,
            modifier = Modifier.weight(.25f)
        )
    }
}