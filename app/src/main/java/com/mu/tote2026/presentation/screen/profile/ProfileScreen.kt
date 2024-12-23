package com.mu.tote2026.presentation.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.presentation.components.AppOutlinedTextField
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.AppRadioGroup
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.PhotoLoad
import com.mu.tote2026.presentation.components.TextError
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.presentation.utils.Errors.ERROR_PROFILE_IS_EMPTY
import com.mu.tote2026.presentation.utils.FEMALE
import com.mu.tote2026.presentation.utils.MALE
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun ProfileScreen(
    toMain: () -> Unit,
    toAuth: () -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: ProfileViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("ProfileScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
                error = ""
            }

            is UiState.Success -> {
                isLoading = false
                error = ""
                toLog("viewModel.exit: ${viewModel.exit}")
                if (viewModel.exit) toMain()
            }

            is UiState.Error -> {
                isLoading = false
                if (result.error == ERROR_PROFILE_IS_EMPTY)
                    toAuth()
                else
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Title(title = stringResource(R.string.profile))
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                CardHeader(
                    viewModel.gambler.email,
                    viewModel.gambler.rate,
                    viewModel.gambler.isAdmin
                )
                HorizontalDivider(thickness = 1.dp)
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    )
                ) {
                    PhotoLoad(
                        photoUrl = viewModel.gambler.photoUrl,
                        onSelect = { uri ->
                            viewModel.onEvent(ProfileEvent.OnPhotoChange(uri))
                        },
                        modifier = Modifier.weight(1f)
                    )
                    ProfileEdit(
                        nickname = viewModel.gambler.nickname,
                        gender = viewModel.gambler.gender,
                        nicknameError = viewModel.nicknameError,
                        genderError = viewModel.genderError,
                        onNicknameChange = { newValue ->
                            viewModel.onEvent(ProfileEvent.OnNicknameChange(newValue))
                        },
                        onGenderChange = { newValue ->
                            viewModel.onEvent(ProfileEvent.OnGenderChange(newValue))
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                HorizontalDivider(thickness = 1.dp)
                OkAndCancel(
                    titleOk = stringResource(R.string.save),
                    enabledOk = viewModel.enabledSaveButton,
                    onOK = { viewModel.onEvent(ProfileEvent.OnSave) },
                    onCancel = { viewModel.onEvent(ProfileEvent.OnCancel) }
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

@Composable
private fun CardHeader(
    email: String,
    rate: Int,
    isAdmin: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = email,
                fontWeight = FontWeight.Bold
            )
            if (rate > 0) {
                Text(
                    text = stringResource(R.string.gambler_rate, rate),
                    fontWeight = FontWeight.Bold
                )
            } else {
                TextError(
                    error = stringResource(R.string.money_is_not_transferred_yet),
                    textAlign = TextAlign.End
                )
            }
        }
        if (isAdmin) {
            Text(
                text = stringResource(R.string.admin),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun ProfileEdit(
    modifier: Modifier = Modifier,
    nickname: String,
    gender: String,
    nicknameError: String?,
    genderError: String?,
    onNicknameChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        AppOutlinedTextField(
            label = "Ник",
            value = nickname,
            onChange = { newValue -> onNicknameChange(newValue) },
            error = nicknameError ?: ""
        )
        Text(
            text = "Пол",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp)
        )
        AppRadioGroup(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp),
            items = listOf(MALE, FEMALE),
            currentValue = gender,
            onClick = { newValue -> onGenderChange(newValue) },
            errorMessage = genderError
        )
    }
}