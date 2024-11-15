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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun GameScreen(
    toGroupGamesList: (GroupGamesDestination) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: GameViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result
    var game by remember { mutableStateOf(GameModel()) }

    LaunchedEffect(key1 = result) {
        toLog("GameScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
                game = result.data

                if (viewModel.exit) toGroupGamesList(GroupGamesDestination(game.group))
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
                Text("${game.team1} - ${game.team2}")
            }

            /*if (GAMBLER.isAdmin) {
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
            }*/
        }
    }
}