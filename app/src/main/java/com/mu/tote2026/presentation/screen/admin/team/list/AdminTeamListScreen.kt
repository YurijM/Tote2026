package com.mu.tote2026.presentation.screen.admin.team.list

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun AdminTeamListScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: AdminTeamListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result
    var teams by remember { mutableStateOf<List<TeamModel>>(listOf()) }

    LaunchedEffect(key1 = result) {
        toLog("AdminTeamListViewModel result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
                teams = result.data
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
        Title(stringResource(R.string.admin_team_list))
        OkAndCancel(
            titleOk = stringResource(R.string.load),
            enabledOk = true,
            showCancel = false,
            onOK = { viewModel.onEvent(AdminTeamListEvent.OnLoad) },
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
                .padding(bottom = 8.dp)
        ) {
            items(teams) { team ->
                AdminTeamListItemScreen(team)
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