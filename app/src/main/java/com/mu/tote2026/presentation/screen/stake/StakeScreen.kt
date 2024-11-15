package com.mu.tote2026.presentation.screen.stake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.data.repository.GAMBLER
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.ByPenalty
import com.mu.tote2026.presentation.components.ExtraTime
import com.mu.tote2026.presentation.components.GameInfo
import com.mu.tote2026.presentation.components.GamesPlayed
import com.mu.tote2026.presentation.components.MainTime
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.TextError
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun StakeScreen(
    toStakeList: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: StakeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("StakeScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false

                if (viewModel.exit) toStakeList()
            }

            is UiState.Error -> {
                isLoading = false
                error = result.error
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Title(title = stringResource(id = R.string.stake))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 8.dp
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier
                        .fillMaxWidth(.85f)
                        .padding(vertical = 8.dp)
                ) {
                    GameInfo(viewModel.game)
                    MainTime(
                        game = viewModel.game,
                        stake = viewModel.stake,
                        errorMessage = viewModel.errorMainTime,
                        onGoal1Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(false, 1, goal)) },
                        onGoal2Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(false, 2, goal)) },
                    )
                    if (viewModel.isExtraTime) {
                        ExtraTime(
                            stake = viewModel.stake,
                            errorMessage = viewModel.errorExtraTime,
                            onAddGoal1Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(true, 1, goal)) },
                            onAddGoal2Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(true, 2, goal)) },
                        )
                        if (viewModel.isByPenalty) {
                            ByPenalty(
                                teams = listOf(
                                    "",
                                    viewModel.game.team1,
                                    viewModel.game.team2
                                ),
                                selectedTeam = viewModel.stake.byPenalty,
                                errorMessage = viewModel.errorByPenalty,
                                onClick = { selectedItem -> viewModel.onEvent(StakeEvent.OnByPenaltyChange(selectedItem)) }
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 8.dp),
                        thickness = 1.dp,
                    )
                    OkAndCancel(
                        titleOk = stringResource(id = R.string.save),
                        enabledOk = viewModel.enabled,
                        onOK = {
                            viewModel.onEvent(StakeEvent.OnSave)
                        },
                        onCancel = toStakeList
                    )
                    if (error.isNotBlank()) {
                        TextError(
                            error = error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            if (viewModel.teamGames.isNotEmpty()) {
                item {
                    Text(
                        text = stringResource(R.string.games_played_yet),
                        fontWeight = FontWeight.Bold
                    )
                }
                items(viewModel.teamGames) { game ->
                    GamesPlayed(
                        game = game,
                        team1 = viewModel.game.team1,
                        team2 = viewModel.game.team2
                    )
                }
            }

            if (GAMBLER.isAdmin) {
                item {
                    OkAndCancel(
                        titleOk = stringResource(R.string.generate_stake),
                        enabledOk = true,
                        showCancel = false,
                        onOK = { viewModel.onEvent(StakeEvent.OnGenerateStake) },
                        onCancel = {}
                    )
                    Text(
                        text = viewModel.generatedStake.value,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }

    if (isLoading) {
        AppProgressBar()
    }
}
