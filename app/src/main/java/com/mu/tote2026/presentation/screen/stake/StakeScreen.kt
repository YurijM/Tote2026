package com.mu.tote2026.presentation.screen.stake

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.StakeModel
import com.mu.tote2026.presentation.components.AppOutlinedTextField
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.TeamFlag
import com.mu.tote2026.presentation.components.TextError
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.asDateTime
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun StakeScreen(
    viewModel: StakeViewModel = hiltViewModel(),
    toStakeList: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

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
                /*EditCard(
                    viewModel = viewModel,
                    error = error
                )*/
                Card(
                    border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline
                    ),
                    modifier = Modifier.fillMaxSize(.85f)
                ) {
                    GameInfo(
                        id = viewModel.game.id,
                        start = viewModel.game.start,
                        group = viewModel.game.group,
                        groupId = viewModel.game.groupId,
                    )
                    MainTime(
                        viewModel.game,
                        viewModel.stake,
                        errorMessage = viewModel.errorMainTime,
                        onGoal1Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(false, 1, goal))  },
                        onGoal2Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(false, 2, goal)) }
                    )

                    if (viewModel.isExtraTime) {
                        ExtraTime(
                            viewModel.stake,
                            errorMessage = viewModel.errorExtraTime,
                            onAddGoal1Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(true, 1, goal)) },
                            onAddGoal2Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(true, 2, goal)) }
                        )
                        /*if (viewModel.isByPenalty) {
                            ByPenalty(
                                teams = listOf(
                                    "",
                                    viewModel.game.team1,
                                    viewModel.game.team2
                                ),
                                selectedTeam = viewModel.game.penalty,
                                errorMessage = viewModel.errorByPenalty,
                                onClick = { selectedItem -> viewModel.onEvent(StakeEvent.OnPenaltyChange(selectedItem)) }
                            )
                        }*/
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 8.dp),
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                    )
                    OkAndCancel(
                        titleOk = stringResource(id = R.string.save),
                        enabledOk = viewModel.enabled,
                        onOK = {
                            viewModel.onEvent(StakeEvent.OnSave) },
                        onCancel = { toStakeList() }
                    )
                    if (error.isNotBlank()) {
                        TextError(
                            error = error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            /*if (GAMBLER.isAdmin) {
                item {
                    OkAndCancel(
                        titleOk = "Сгенерировать ставку",
                        enabledOk = true,
                        showCancel = false,
                        onOK = { *//*viewModel.onEvent(StakeEvent.OnGenerateStake)*//* },
                        onCancel = {}
                    )
                    Text(
                        text = "", //viewModel.generatedStake.value,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }*/
        }
    }

    if (isLoading) {
        AppProgressBar()
    }
}

/*@Composable
private fun EditCard(
    viewModel: StakeViewModel,
    error: String
) {
    Card(
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.outline
        ),
        modifier = Modifier.fillMaxSize(.85f)
        *//*modifier = Modifier
            .width(400.dp)
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 12.dp
            ),*//*
    ) {
        GameInfo(
            id = viewModel.game.id,
            start = viewModel.game.start,
            group = viewModel.game.group,
            groupId = viewModel.game.groupId,
        )
        *//*MainTime(
            team1 = viewModel.game.team1,
            team2 = viewModel.game.team2,
            goal1 = viewModel.game.goal1,
            goal2 = viewModel.game.goal2,
            flags = viewModel.flags.first { it.gameId == viewModel.game.gameId },
            errorMessage = viewModel.errorMainTime,
            onGoal1Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(false, 1, goal)) },
            onGoal2Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(false, 2, goal)) }
        )*//*
        *//*if (viewModel.isExtraTime) {
            ExtraTime(
                addGoal1 = viewModel.game.addGoal1,
                addGoal2 = viewModel.game.addGoal2,
                errorMessage = viewModel.errorExtraTime,
                onAddGoal1Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(true, 1, goal)) },
                onAddGoal2Change = { goal -> viewModel.onEvent(StakeEvent.OnGoalChange(true, 2, goal)) }
            )
            if (viewModel.isByPenalty) {
                ByPenalty(
                    teams = listOf(
                        "",
                        viewModel.game.team1,
                        viewModel.game.team2
                    ),
                    selectedTeam = viewModel.game.penalty,
                    errorMessage = viewModel.errorByPenalty,
                    onClick = { selectedItem -> viewModel.onEvent(StakeEvent.OnPenaltyChange(selectedItem)) }
                )
            }
        }*//*
        *//*OkAndCancel(
            titleOk = stringResource(id = R.string.save),
            enabledOk = viewModel.enabled,
            onOK = { viewModel.onEvent(StakeEvent.OnSave) },
            onCancel = { viewModel.onEvent(StakeEvent.OnCancel) }
        )*//*
        if (error.isNotBlank()) {
            TextError(
                error = error,
                textAlign = TextAlign.Center
            )
            //Spacer(modifier = Modifier.height(4.dp))
        }
    }
}*/

@Composable
private fun GameInfo(
    id: String,
    start: String,
    group: String,
    groupId: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        text = if (start.isNotBlank()) start.asDateTime() else start,
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
            text = stringResource(R.string.game_number, id)
        )
        Text(
            modifier = Modifier.weight(1f),
            text = if (groupId.isNotBlank() && groupId.toInt() <= GROUPS_COUNT)
                stringResource(R.string.group_name, group)
            else group,
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun MainTime(
    game: GameModel,
    stake: StakeModel,
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
                value = stake.goal1,
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
                value = stake.goal2,
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

@Composable
private fun ExtraTime(
    stake: StakeModel,
    errorMessage: String,
    onAddGoal1Change: (String) -> Unit,
    onAddGoal2Change: (String) -> Unit,
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.add_time_score),
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

/*@Composable
fun ByPenalty(
    teams: List<String>,
    selectedTeam: String,
    errorMessage: String,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AppDropDownList(
            modifier = Modifier.width(180.dp),
            list = teams,
            label = stringResource(R.string.by_penalty),
            selectedItem = selectedTeam,
            onClick = { selectedItem -> onClick(selectedItem) }
        )
    }
    if (errorMessage.isNotBlank()) {
        TextError(
            error = errorMessage,
            textAlign = TextAlign.Center
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 8.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.onSurface,
    )
}

@Composable
private fun GamePlayed(
    game: GameModel,
    flags: GameFlagsModel,
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
                    text = game.team1,
                    fontWeight = if (game.team1 in listOf(team1, team2))
                        FontWeight.Bold
                    else
                        FontWeight.Normal
                )
                ShowFlag(flags.flag1)
                Text(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    text = "${game.goal1}:${game.goal2}"
                )
                ShowFlag(flags.flag2)
                Text(
                    modifier = Modifier.weight(1f),
                    text = game.team2,
                    fontWeight = if (game.team2 in listOf(team1, team2))
                        FontWeight.Bold
                    else
                        FontWeight.Normal
                )
            }
            if (game.addGoal1.isNotBlank()) {
                Text(
                    */
/*text = "${game.goal1}:${game.goal2}" +
                            if (game.addGoal1.isNotBlank())
                                ", ${stringResource(id = R.string.add_time)} ${game.addGoal1}:${game.addGoal2}" +
                                        if (game.penalty.isNotBlank())
                                            ", ${stringResource(id = R.string.by_penalty)} ${game.penalty}"
                                        else ""
                            else ""*//*

                    text = "${stringResource(id = R.string.add_time)} ${game.addGoal1}:${game.addGoal2}" +
                            if (game.penalty.isNotBlank())
                                ", ${stringResource(id = R.string.by_penalty)} ${game.penalty}"
                            else ""
                )
            }
        }
    }
}*/
