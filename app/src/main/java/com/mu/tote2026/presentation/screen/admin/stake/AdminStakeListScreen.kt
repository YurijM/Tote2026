package com.mu.tote2026.presentation.screen.admin.stake

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.resultToString
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun AdminStakeListScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: AdminStakeListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result
    var games by remember { mutableStateOf<List<GameModel>>(listOf()) }

    LaunchedEffect(key1 = result) {
        toLog("AdminStakeListScreen result: $result")
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
        Title(stringResource(R.string.admin_stake_list))
        OkAndCancel(
            titleOk = stringResource(R.string.load),
            enabledOk = true,
            showCancel = false,
            onOK = { viewModel.onEvent(AdminStakeListEvent.OnLoad) },
            onCancel = {}
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary,
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            items(games) { game ->
                Text(game.id)
                game.stakes.forEach { stake ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(
                            text = stake.gamblerNickname,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = resultToString(
                                goal1 = stake.goal1,
                                goal2 = stake.goal2,
                                addGoal1 = stake.addGoal1,
                                addGoal2 = stake.addGoal2,
                                byPenalty = stake.byPenalty
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1.5f)
                        )
                    }
                }
            }
        }
    }

    if (isLoading) {
        AppProgressBar()
    }

    if (error.isNotBlank()) {
        val context = LocalContext.current
        Toast.makeText(context, errorTranslate(error), Toast.LENGTH_LONG).show()
    }
}