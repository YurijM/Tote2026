package com.mu.tote2026.presentation.screen.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.mu.tote2026.presentation.components.AddTime
import com.mu.tote2026.presentation.components.ByPenalty
import com.mu.tote2026.presentation.components.GameIdAndGroup
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.SetDate
import com.mu.tote2026.presentation.components.SetTime
import com.mu.tote2026.presentation.components.StartGame
import com.mu.tote2026.presentation.components.TeamResult
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.asDate
import com.mu.tote2026.presentation.utils.asDateTime
import com.mu.tote2026.presentation.utils.convertDateTimeToTimestamp
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalMaterial3Api
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

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()

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
                    StartGame(
                        date = viewModel.game.start.asDateTime(),
                        error = viewModel.errorStart,
                        onClick = { showDatePicker = true }
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            bottom = 8.dp
                        )
                    )
                    GameIdAndGroup(
                        gameId = viewModel.game.id,
                        error = viewModel.errorGameId,
                        group = viewModel.game.group,
                        onChange = { newValue ->
                            viewModel.onEvent(GameEvent.OnGameIdChange(newValue))
                        },
                        onClick = { selectedItem ->
                            viewModel.onEvent(GameEvent.OnGroupChange(selectedItem))
                        }
                    )
                    TeamResult(
                        teams = viewModel.teamList,
                        team = viewModel.game.team1,
                        goal = viewModel.game.goal1,
                        error = viewModel.errorGoal1,
                        onTeamSelect = { team -> viewModel.onEvent(GameEvent.OnTeamChange(1, team)) },
                        onGoalSet = { goal -> viewModel.onEvent(GameEvent.OnGoalChange(false, 1, goal)) }
                    )
                    TeamResult(
                        teams = viewModel.teamList,
                        team = viewModel.game.team2,
                        goal = viewModel.game.goal2,
                        error = viewModel.errorGoal2,
                        onTeamSelect = { team -> viewModel.onEvent(GameEvent.OnTeamChange(2, team)) },
                        onGoalSet = { goal -> viewModel.onEvent(GameEvent.OnGoalChange(false, 2, goal)) }
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    if (viewModel.isAddTime) {
                        AddTime(
                            game = viewModel.game,
                            error = viewModel.errorAddTime,
                            onAddGoal1Change = { goal ->
                                viewModel.onEvent(GameEvent.OnGoalChange(true, 1, goal))
                            },
                            onAddGoal2Change = { goal ->
                                viewModel.onEvent(GameEvent.OnGoalChange(true, 2, goal))
                            }
                        )
                        HorizontalDivider(
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        if (viewModel.isByPenalty) {
                            ByPenalty(
                                teams = listOf(
                                    "",
                                    viewModel.game.team1,
                                    viewModel.game.team2
                                ),
                                error = viewModel.errorByPenalty,
                                selectedTeam = viewModel.game.byPenalty,
                                onClick = { selectedItem ->
                                    viewModel.onEvent(GameEvent.OnByPenaltyChange(selectedItem))
                                }
                            )
                            HorizontalDivider(
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                    OkAndCancel(
                        titleOk = stringResource(id = R.string.save),
                        enabledOk = viewModel.enabled,
                        onOK = { viewModel.onEvent(GameEvent.OnSave) },
                        onCancel = { if (viewModel.game.group.isNotBlank() && viewModel.game.groupId.toInt() <= GROUPS_COUNT)
                            toGroupGamesList(GroupGamesDestination(viewModel.game.group))
                        else
                            toGameList()
                        }
                    )
                }
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

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = if (viewModel.game.start.isNotBlank()) {
                    viewModel.game.start.toLong()
                } else {
                    calendar.timeInMillis
                }
            )

            SetDate(
                datePickerState = datePickerState,
                onDismissRequest = { showDatePicker = false },
                onClickConfirm = {
                    val date = "${(datePickerState.selectedDateMillis!!).toString().asDate()} ${viewModel.startTime}"
                    val selectedDate = convertDateTimeToTimestamp(date).toLong()
                    viewModel.onEvent(GameEvent.OnStartChange(selectedDate.toString()))
                    showDatePicker = false
                    showTimePicker = true
                },
                onClickCancel = { showDatePicker = false }
            )
        }
        if (showTimePicker) {
            val time = viewModel.startTime.split(":")

            var selectedHour by remember { mutableIntStateOf(time[0].toInt()) }
            var selectedMinute by remember { mutableIntStateOf(time[1].toInt()) }
            val timePickerState = rememberTimePickerState(
                initialHour = selectedHour,
                initialMinute = selectedMinute
            )

            SetTime(
                timePickerState,
                onDismissRequest = { },
                onClickConfirm = {
                    selectedHour = timePickerState.hour
                    selectedMinute = timePickerState.minute
                    val date = "${viewModel.game.start.asDate()} $selectedHour:$selectedMinute"
                    val selectedDate = convertDateTimeToTimestamp(date).toLong()
                    viewModel.onEvent(GameEvent.OnStartChange(selectedDate.toString()))
                    showTimePicker = false
                },
                onClickCancel = { showTimePicker = false }
            )
        }
    }
}
