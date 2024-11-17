package com.mu.tote2026.presentation.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.data.repository.GAMBLER
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun GameScreen(
    toGroupGamesList: (GroupGamesDestination) -> Unit,
    toGameList: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: GameViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("GameScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false

                if (viewModel.exit) {
                    if (viewModel.game.groupId.toInt() <= GROUPS_COUNT)
                        toGroupGamesList(GroupGamesDestination(viewModel.game.group))
                    else
                        toGameList()
                }
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
        Title(title = stringResource(id = R.string.game))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                /*Card(
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
                        errorMessage = viewModel.errorMainTime,
                        onGoal1Change = { goal -> viewModel.onEvent(GameEvent.OnGoalChange(false, 1, goal)) },
                        onGoal2Change = { goal -> viewModel.onEvent(GameEvent.OnGoalChange(false, 2, goal)) },
                    )
                    if (viewModel.isExtraTime) {
                        ExtraTime(
                            game = viewModel.game,
                            errorMessage = viewModel.errorExtraTime,
                            onAddGoal1Change = { goal -> viewModel.onEvent(GameEvent.OnGoalChange(true, 1, goal)) },
                            onAddGoal2Change = { goal -> viewModel.onEvent(GameEvent.OnGoalChange(true, 2, goal)) }
                        )
                        if (viewModel.isByPenalty) {
                            ByPenalty(
                                teams = listOf(
                                    "",
                                    viewModel.game.team1,
                                    viewModel.game.team2
                                ),
                                selectedTeam = viewModel.game.byPenalty,
                                errorMessage = viewModel.errorByPenalty,
                                onClick = { selectedItem -> viewModel.onEvent(GameEvent.OnByPenaltyChange(selectedItem)) }
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
                        onOK = { viewModel.onEvent(GameEvent.OnSave) },
                        onCancel = { if (viewModel.game.groupId.toInt() <= GROUPS_COUNT)
                            toGroupGamesList(GroupGamesDestination(viewModel.game.group))
                        else
                            toGameList()
                        }
                    )
                    if (error.isNotBlank()) {
                        TextError(
                            error = error,
                            textAlign = TextAlign.Center
                        )
                    }
                }*/
            }

            if (GAMBLER.isAdmin) {
                item {
                    OkAndCancel(
                        titleOk = stringResource(R.string.generate_result),
                        enabledOk = true,
                        showCancel = false,
                        onOK = { viewModel.onEvent(GameEvent.OnGenerateGame) },
                        onCancel = {}
                    )
                    Text(
                        text = viewModel.generatedGame.value,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
