package com.mu.tote2026.presentation.screen.admin.team

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.presentation.components.AppDropDownList
import com.mu.tote2026.presentation.components.AppOutlinedTextField
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.PhotoLoad
import com.mu.tote2026.presentation.components.TextError
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.GROUPS
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun AdminTeamScreen(
    viewModel: AdminTeamViewModel = hiltViewModel(),
    toAdminTeamList: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("AdminTeamScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
                error = ""
            }

            is UiState.Success -> {
                isLoading = false
                error = ""

                if (viewModel.exit) toAdminTeamList()
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Title(title = if (viewModel.isEdit) stringResource(R.string.team) else viewModel.team.team)
            Card(
                modifier = Modifier.fillMaxWidth(.85f),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                if (viewModel.isEdit)
                    AppOutlinedTextField(
                        label = "Команда",
                        value = viewModel.team.team,
                        onChange = { newValue -> viewModel.onEvent(AdminTeamEvent.OnTeamChange(newValue)) },
                        error = viewModel.teamError
                    )
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(.5f)
                    ) {
                        AppDropDownList(
                            list = GROUPS.subList(0, GROUPS_COUNT - 1),
                            label = stringResource(R.string.group),
                            selectedItem = viewModel.team.group,
                            //width = 140.dp,
                            onClick = { selectedItem -> viewModel.onEvent(AdminTeamEvent.OnGroupChange(selectedItem)) }
                        )
                        AppOutlinedTextField(
                            label = "Номер",
                            value = viewModel.team.itemNo,
                            onChange = { newValue -> viewModel.onEvent(AdminTeamEvent.OnItemNoChange(newValue)) },
                            error = viewModel.itemNoError,
                            textAlign = TextAlign.Center,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword
                            ),
                        )
                    }
                    PhotoLoad(
                        photoUrl = viewModel.team.flag,
                        size = dimensionResource(R.dimen.flag_edit_size),
                        onSelect = { uri ->
                            viewModel.onEvent(AdminTeamEvent.OnFlagChange(uri))
                        },
                    )
                }
                HorizontalDivider(thickness = 1.dp)
                OkAndCancel(
                    enabledOk = viewModel.enabledSaveButton,
                    onOK = { viewModel.onEvent(AdminTeamEvent.OnSave) },
                    onCancel = { toAdminTeamList() }
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