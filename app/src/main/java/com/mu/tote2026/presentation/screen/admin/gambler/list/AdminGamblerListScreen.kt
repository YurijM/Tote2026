package com.mu.tote2026.presentation.screen.admin.gambler.list

import android.annotation.SuppressLint
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@SuppressLint("DefaultLocale")
@Composable
fun AdminGamblerListScreen(
    toGamblerEdit: (String) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: AdminGamblerListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result
    var gamblers by remember { mutableStateOf<List<GamblerModel>>(listOf()) }

    LaunchedEffect(key1 = result) {
        toLog("AdminGamblerListScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
                gamblers = result.data
            }

            is UiState.Error -> {
                isLoading = false
                error = result.error
            }

            else -> {}
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title(stringResource(R.string.admin_gambler_list))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.prize_fund) + ":",
                textAlign = TextAlign.End,
                lineHeight = 0.85.em,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1.35f)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ) {
                        append(viewModel.prizeFund.toString())
                    }
                    append(" руб.")
                },
                lineHeight = 0.85.em,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.winner_played) + ":",
                textAlign = TextAlign.End,
                lineHeight = 0.85.em,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1.35f)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ) {
                        append(String.format("%.2f", viewModel.winnerPlayed))
                    }
                    append(" руб.")
                },
                lineHeight = 0.85.em,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.cash_in_hand) + ":",
                textAlign = TextAlign.End,
                lineHeight = 0.85.em,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1.35f)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ) {
                        append(viewModel.cashInHand.toString())
                    }
                    append(" руб.")
                },
                lineHeight = 0.85.em,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        ) {
            Text(
                text = stringResource(R.string.rested_prize_fund) + ":",
                textAlign = TextAlign.End,
                lineHeight = 0.85.em,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .weight(1.35f)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ) {
                        append(String.format("%.2f", viewModel.restedPrizeFund))
                    }
                    append(" руб.")
                },
                lineHeight = 0.85.em,
                modifier = Modifier.weight(1f)
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            items(gamblers) { gambler ->
                AdminGamblerListItemScreen(
                    gambler,
                    winner = viewModel.winners.find { it.gamblerId == gambler.id },
                    onEdit = { toGamblerEdit(gambler.id )}
                )
            }
        }
    }

    if (isLoading) {
        AppProgressBar()
    }
}