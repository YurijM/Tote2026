package com.mu.tote2026.presentation.screen.admin.prize_fund

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@SuppressLint("DefaultLocale")
@Composable
fun AdminPrizeFundScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: AdminPrizeFundViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("AdminPrizeFundScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
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
        Title(title = stringResource(id = R.string.admin_prize_fund))
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
                        .fillMaxWidth(.9f)
                        .padding(
                            top = 4.dp,
                            bottom = 12.dp
                        )
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    ParamItem(
                        param = "Общий призовой фонд",
                        value = viewModel.commonParams.prizeFund.toString() + " руб.",
                        isTitle = true
                    )
                    ParamItem(
                        param = "Призовой фонд группового этапа\n" +
                                "(1/3 общего призового фонда)",
                        value = String.format("%.2f", viewModel.commonParams.groupPrizeFund) + " руб.",
                    )
                    ParamItem(
                        param = "Призовой фонд игр плэйофф\n" +
                                "(1/3 общего призового фонда)",
                        value = String.format("%.2f", viewModel.commonParams.playoffPrizeFund) + " руб.",
                    )
                    ParamItem(
                        param = "Призовой фонд победителей в зависимости от взноса (1/6 общего призового фонда)",
                        value = String.format("%.2f", viewModel.commonParams.winnersPrizeFundByStake) + " руб.",
                    )
                    ParamItem(
                        param = "Призовой фонд победителей в зависимости от разницы набранных очков (1/6 общего призового " +
                                "фонда)",
                        value = String.format("%.2f", viewModel.commonParams.winnersPrizeFund) + " руб.",
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        if (isLoading) {
            AppProgressBar()
        }
    }
}

@Composable
private fun ParamItem(
    param: String,
    value: String,
    isTitle: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 4.dp,
                end = 4.dp,
                bottom = 8.dp
            )
    ) {
        Text(
            text = param,
            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
            textAlign = TextAlign.End,
            lineHeight = 1.25.em,
            modifier = Modifier
                .weight(2.5f)
                .padding(end = 8.dp),
            fontWeight = if (isTitle) FontWeight.Bold else FontWeight.Normal,
        )
        Text(
            text = value,
            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
    }
    HorizontalDivider(
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}