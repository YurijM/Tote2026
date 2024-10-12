package com.mu.tote2026.presentation.screen.admin.email.list

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2024.presentation.components.AppFabAdd
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.EmailModel
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.ApplicationDialog
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.NEW_DOC
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun AdminEmailListScreen(
    viewModel: AdminEmailListViewModel = hiltViewModel(),
    toEmailEdit: (String) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val result = state.result

    var currentEmail by remember { mutableStateOf(EmailModel()) }

    var openDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = result) {
        toLog("AdminEmailListScreen result: $result")
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
        modifier = Modifier.fillMaxSize()
    ) {
        Title(stringResource(R.string.admin_email_list))

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(viewModel.emailList) { email ->
                AdminEmailListItemScreen(
                    email = email.email,
                    onEdit = { toEmailEdit(email.id) },
                    onDelete = {
                        currentEmail = email
                        openDialog = true
                    }
                )
            }
        }
    }
    AppFabAdd(
        onAdd = { toEmailEdit(NEW_DOC) }
    )

    if (openDialog) {
        ApplicationDialog(
            title = stringResource(R.string.email_delete),
            text = stringResource(R.string.want_delete_email, currentEmail.email),
            titleOK = stringResource(R.string.yes),
            titleCancel = stringResource(R.string.no),
            showCancel = true,
            onDismiss = { openDialog = false },
            onOK = {
                openDialog = false
                viewModel.onEvent((AdminEmailListEvent.OnDelete(currentEmail)))
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