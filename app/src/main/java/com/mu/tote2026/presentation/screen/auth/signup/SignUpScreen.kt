package com.mu.tote2026.presentation.screen.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.AppTextField
import com.mu.tote2026.presentation.components.OkAndCancel
import com.mu.tote2026.presentation.components.PasswordTextField
import com.mu.tote2026.presentation.components.TextError
import com.mu.tote2026.presentation.components.Title
import com.mu.tote2026.ui.common.UiState

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val isLoading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    when (state.result) {
        is UiState.Loading -> {
            isLoading.value = true
            error.value = ""
        }

        is UiState.Success -> {
            isLoading.value = false
            error.value = ""
        }

        is UiState.Error -> {
            isLoading.value = false
            error.value = (state.result as UiState.Error).error
        }

        else -> {}
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .weight(2f)
            ) {
                Title(titleId = R.string.sign_up)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 12.dp,
                                start = 12.dp,
                                end = 12.dp,
                                bottom = 16.dp,
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AppTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            value = viewModel.email,
                            onChange = { newValue ->
                                viewModel.onEvent(SignUpEvent.OnEmailChange(newValue))
                            },
                            label = stringResource(id = R.string.enter_email),
                            painterId = R.drawable.ic_email,
                            description = "email",
                            errorMessage = viewModel.errorEmail,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                            )
                        )
                        PasswordTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            label = stringResource(id = R.string.enter_password),
                            value = viewModel.password,
                            onChange = { newValue ->
                                viewModel.onEvent(SignUpEvent.OnPasswordChange(newValue))
                            },
                            painterId = R.drawable.ic_password,
                            description = "password",
                            errorMessage = viewModel.errorPassword
                        )
                        PasswordTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            label = stringResource(id = R.string.confirm_password),
                            value = viewModel.passwordConfirm,
                            onChange = { newValue ->
                                viewModel.onEvent(SignUpEvent.OnPasswordConfirmChange(newValue))
                            },
                            painterId = R.drawable.ic_password,
                            description = "password confirm",
                            errorMessage = viewModel.errorPasswordConfirm
                        )
                        OkAndCancel(
                            titleOk = stringResource(id = R.string.to_register),
                            enabledOk = viewModel.enabledButton,
                            showCancel = false,
                            onOK = {
                                viewModel.onEvent(SignUpEvent.OnSignUp)
                            },
                            onCancel = {}
                        )
                        if (error.value.isNotBlank()) {
                            TextError(
                                errorMessage = error.value,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.field1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        if (isLoading.value) AppProgressBar()
    }
}