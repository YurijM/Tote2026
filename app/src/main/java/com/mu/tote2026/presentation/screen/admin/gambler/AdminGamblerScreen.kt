package com.mu.tote2026.presentation.screen.admin.gambler

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.presentation.components.AppOutlinedTextField
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.TextError
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun AdminGamblerScreen(
    viewModel: AdminGamblerViewModel = hiltViewModel(),
    toAdminGamblerList: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("AdminGamblerScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false

                if (viewModel.exit) toAdminGamblerList()
            }

            is UiState.Error -> {
                isLoading = false
                error = errorTranslate(result.error)
            }

            else -> {}
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.fillMaxWidth(.75f),
            ) {
                AppOutlinedTextField(
                    label = stringResource(R.string.transferred_money),
                    value = viewModel.gambler.rate.toString(),
                    onChange = { newValue -> viewModel.onEvent(AdminGamblerEvent.OnRateChange(newValue)) },
                    error = viewModel.rateError,
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 8.dp
                    )
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentWidth()
                        .padding(bottom = 8.dp)
                        .toggleable(
                            value = viewModel.gambler.isAdmin,
                            onValueChange = { newValue ->
                                viewModel.onEvent(AdminGamblerEvent.OnIsAdminChange(newValue))
                            },
                            role = Role.Checkbox
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(
                        checked = viewModel.gambler.isAdmin,
                        onCheckedChange = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(R.string.admin),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                HorizontalDivider(thickness = 1.dp)
                OkAndCancel(
                    titleOk = stringResource(R.string.save),
                    enabledOk = viewModel.rateError.isBlank(),
                    onOK = { viewModel.onEvent(AdminGamblerEvent.OnSave) },
                    onCancel = { toAdminGamblerList() }
                )
                if (error.isNotBlank()) {
                    TextError(
                        error = error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        if (isLoading) {
            AppProgressBar()
        }
    }
}