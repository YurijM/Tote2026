package com.mu.tote2026.presentation.screen.admin.finish

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2024.presentation.screen.admin.finish.AdminFinishEvent
import com.mu.tote2026.R
import com.mu.tote2026.presentation.components.AppOutlinedTextField
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.ui.common.UiState

@Composable
fun AdminFinishScreen() {
    val viewModel: AdminFinishViewModel = hiltViewModel()

    val isLoading = remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    when (state.result) {
        is UiState.Loading -> {
            isLoading.value = true
        }

        is UiState.Success -> {
            isLoading.value = false
            /*isLoaded = true
            if (viewModel.isExit) toAdmin()*/
        }

        is UiState.Error -> {
            isLoading.value = false
        }

        else -> {}
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Title(stringResource(R.string.admin_finish))
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.fillMaxWidth(.9f),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .toggleable(
                            value = viewModel.finish.finished,
                            onValueChange = { newValue ->
                                viewModel.onEvent(AdminFinishEvent.OnFinishedChange(newValue))
                            },
                            role = Role.Checkbox
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.tournament_finished),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(end = 4.dp),
                    )
                    Checkbox(
                        checked = viewModel.finish.finished,
                        onCheckedChange = null
                    )
                }
                AppOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 8.dp,
                            end = 8.dp,
                            bottom = 8.dp,
                        ),
                    label = stringResource(id = R.string.admin_finish),
                    value = viewModel.finish.champion,
                    onChange = { newValue ->
                        viewModel.onEvent(AdminFinishEvent.OnChampionChange(newValue))
                    },
                    error = "",
                )
                HorizontalDivider(
                    thickness = 1.dp
                )
                OkAndCancel(
                    enabledOk = true,
                    onOK = { viewModel.onEvent(AdminFinishEvent.OnSave) },
                    onCancel = { viewModel.onEvent(AdminFinishEvent.OnCancel) }
                )
            }
        }
    }
}