package com.mu.tote2026.presentation.screen.admin.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.presentation.components.AppOutlinedTextField
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@SuppressLint("DefaultLocale")
@Composable
fun AdminCommonParamsScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: AdminCommonParamsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("AdminCommonParamsScreen result: $result")
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
        Title(title = stringResource(id = R.string.common_params))
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
                    val prizeFund = viewModel.commonParams.prizeFund.toDoubleOrNull()
                    val winnersPrizeFund = viewModel.commonParams.winnersPrizeFund.toDoubleOrNull()

                    ParamItem(
                        param = "Общий призовой фонд (" +
                                "${viewModel.prizeFund}" +
                                " руб.)",
                        value = viewModel.commonParams.prizeFund,
                        error = viewModel.errors.prizeFundError,
                    ) { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnPrizeFundChange(newValue)) }

                    ParamItem(
                        param = "Призовой фонд группового этапа (" +
                                if (prizeFund != null) {
                                    "${String.format("%.4f", prizeFund / 3.0)} руб.)"
                                } else "0.0 руб.)",
                        value = viewModel.commonParams.groupPrizeFund,
                        error = viewModel.errors.groupPrizeFundError,
                    ) { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnGroupPrizeFundChange(newValue)) }

                    ParamItem(
                        param = "Призовой фонд игр плэйофф (" +
                                if (prizeFund != null) {
                                    "${String.format("%.4f", prizeFund / 3.0)} руб.)"
                                } else "0.0 руб.)",
                        value = viewModel.commonParams.playoffPrizeFund,
                        error = viewModel.errors.playoffPrizeFundError,
                    ) { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnPlayoffPrizeFundChange(newValue)) }

                    ParamItem(
                        param = "Призовой фонд победителей (" +
                                if (prizeFund != null) {
                                    "${String.format("%.4f", prizeFund * 2.0 / 9.0)} руб.)"
                                } else "0.0 руб.)",
                        value = viewModel.commonParams.winnersPrizeFund,
                        error = viewModel.errors.winnersPrizeFundError,
                    ) { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnWinnersPrizeFundChange(newValue)) }

                    ParamItem(
                        param = "Призовой фонд за 1 место (" +
                                if (winnersPrizeFund != null) {
                                    "${String.format("%.4f", winnersPrizeFund / 2.0)} руб.)"
                                } else "0.0 руб.)",
                        value = viewModel.commonParams.place1PrizeFund,
                        error = viewModel.errors.place1PrizeFundError,
                    ) { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnPlace1PrizeFundChange(newValue)) }

                    ParamItem(
                        param = "Призовой фонд за 2 место (" +
                                if (winnersPrizeFund != null) {
                                    "${String.format("%.4f", winnersPrizeFund / 3.0)} руб.)"
                                } else "0.0 руб.)",
                        value = viewModel.commonParams.place2PrizeFund,
                        error = viewModel.errors.place2PrizeFundError,
                    ) { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnPlace2PrizeFundChange(newValue)) }

                    ParamItem(
                        param = "Призовой фонд за 3 место (" +
                                if (winnersPrizeFund != null) {
                                    "${String.format("%.4f", winnersPrizeFund / 6.0)} руб.)"
                                } else "0.0 руб.)",
                        value = viewModel.commonParams.place3PrizeFund,
                        error = viewModel.errors.place3PrizeFundError,
                    ) { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnPlace3PrizeFundChange(newValue)) }

                    ParamItem(
                        param = "Призовой фонд победителей в зависимости от взноса (" +
                                if (prizeFund != null) {
                                    "${String.format("%.4f", prizeFund / 9.0)} руб.)"
                                } else "0.0 руб.)",
                        value = viewModel.commonParams.winnersPrizeFundByStake,
                        error = viewModel.errors.winnersPrizeFundByStakeError,
                    ) { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnWinnersPrizeFundByStakeChange(newValue)) }

                    /*Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Общий призовой фонд (" +
                                    "${viewModel.prizeFund}" +
                                    " руб.)",
                            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
                            textAlign = TextAlign.End,
                            lineHeight = 1.25.em,
                            modifier = Modifier.weight(2f)
                        )
                        AppOutlinedTextField(
                            value = viewModel.commonParams.prizeFund,
                            onChange = { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnPrizeFundChange(newValue)) },
                            error = viewModel.errors.prizeFundError,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }*/

                    /*Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Призовой фонд группового этапа (" +
                                    String.format("%.4f", viewModel.commonParams.prizeFund.toDouble() / 3.0) +
                                    " руб.)",
                            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
                            textAlign = TextAlign.End,
                            lineHeight = 1.25.em,
                            modifier = Modifier.weight(2f)
                        )
                        AppOutlinedTextField(
                            value = viewModel.commonParams.groupPrizeFund,
                            onChange = { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnGroupPrizeFundChange(newValue)) },
                            error = viewModel.errors.groupPrizeFundError,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }*/

                    /*Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Призовой фонд игр плэйофф (" +
                                    String.format("%.4f", viewModel.commonParams.prizeFund.toDouble() / 3.0) +
                                    " руб.)",
                            textAlign = TextAlign.End,
                            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
                            lineHeight = 1.25.em,
                            modifier = Modifier.weight(2f)
                        )
                        AppOutlinedTextField(
                            value = viewModel.commonParams.playoffPrizeFund,
                            onChange = { newValue -> viewModel.onEvent(AdminCommonParamsEvent.OnPlayoffPrizeFundChange(newValue)) },
                            error = viewModel.errors.playoffPrizeFundError,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Decimal
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }*/

                    HorizontalDivider(thickness = 1.dp)
                    OkAndCancel(
                        enabledOk = viewModel.enabled,
                        onOK = { viewModel.onEvent(AdminCommonParamsEvent.OnSave) },
                        onCancel = { }
                    )
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
    error: String,
    onChange: (String) ->Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = param,
            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
            textAlign = TextAlign.End,
            lineHeight = 1.25.em,
            modifier = Modifier.weight(1.75f)
        )
        AppOutlinedTextField(
            value = value,
            onChange = { newValue -> onChange(newValue) },
            error = error,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.weight(1f)
        )
    }
}