package com.mu.tote2026.presentation.screen.admin.group.list

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
import com.mu.tote2024.presentation.components.AppFabAdd
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GroupModel
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.ApplicationDialog
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.NEW_DOC
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun AdminGroupListScreen(
    toGroupEdit: (String) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: AdminGroupListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result
    var groups by remember { mutableStateOf<List<GroupModel>>(listOf()) }

    var currentGroup by remember { mutableStateOf(GroupModel()) }

    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = result) {
        toLog("AdminGroupListViewModel result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
                groups = result.data
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
        Title(stringResource(R.string.admin_group_list))
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
            items(groups) { group ->
                AdminGroupListItemScreen(
                    group = group.group,
                    onEdit = { toGroupEdit(group.id) },
                    onDelete = {
                        currentGroup = group
                        openDialog = true
                    }
                )
            }
        }
    }
    AppFabAdd(
        onAdd = { toGroupEdit(NEW_DOC) }
    )

    if (openDialog) {
        ApplicationDialog(
            title = stringResource(R.string.group_delete),
            text = stringResource(R.string.want_delete_group, currentGroup.group),
            titleOK = stringResource(R.string.yes),
            titleCancel = stringResource(R.string.no),
            showCancel = true,
            onDismiss = { openDialog = false },
            onOK = {
                openDialog = false
                viewModel.onEvent((AdminGroupListEvent.OnDelete(currentGroup)))
            },
            onCancel = { openDialog = false }
        )
    }

    if (isLoading) {
        AppProgressBar()
    }

    if (error.isNotBlank() || viewModel.message.value.isNotBlank()) {
        val context = LocalContext.current
        val message = error.ifBlank {
            viewModel.message.value
        }.toString()

        Toast.makeText(context, errorTranslate(message), Toast.LENGTH_LONG).show()
    }
}