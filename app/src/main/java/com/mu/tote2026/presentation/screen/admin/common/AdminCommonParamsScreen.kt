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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Общий призовой фонд (" +
                                    "${viewModel.prizeFund}" +
                                    " руб.)",
                            textAlign = TextAlign.End,
                            lineHeight = 1.25.em,
                            modifier = Modifier.weight(3f)
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
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = "Призовой фонд группового этапа (" +
                                    String.format("%.4f", viewModel.commonParams.prizeFund.toDouble() / 3.0) +
                                    " руб.)",
                            textAlign = TextAlign.End,
                            lineHeight = 1.25.em,
                            modifier = Modifier.weight(3f)
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
                    }

                    Row(
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
                            lineHeight = 1.25.em,
                            modifier = Modifier.weight(3f)
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
                    }

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