package com.mu.tote2026.presentation.screen.auth.signup

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.SignCard
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    toProfile: () -> Unit
) {
    val isLoading = remember { mutableStateOf(false) }
    val error = remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(key1 = result) {
        toLog("result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading.value = true
                error.value = ""
            }

            is UiState.Success -> {
                isLoading.value = false
                error.value = ""
                toProfile()
            }

            is UiState.Error -> {
                isLoading.value = false
                error.value = errorTranslate(result.error)
            }

            else -> {}
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        SignCard(
            titleId = R.string.sign_up,
            titleOkId = R.string.to_register,
            email = viewModel.email,
            onEmailChange = { newValue -> viewModel.onEvent(SignUpEvent.OnEmailChange(newValue)) },
            errorEmail = viewModel.errorEmail,
            password = viewModel.password,
            onPasswordChange = { newValue -> viewModel.onEvent(SignUpEvent.OnPasswordChange(newValue)) },
            errorPassword = viewModel.errorPassword,
            isRegistration = true,
            passwordConfirm = viewModel.passwordConfirm,
            onPasswordConfirmChange = { newValue -> viewModel.onEvent(SignUpEvent.OnPasswordConfirmChange(newValue)) },
            errorPasswordConfirm = viewModel.errorPasswordConfirm,
            enabledButton = viewModel.enabledButton,
            onSign = { viewModel.onEvent(SignUpEvent.OnSignUp) },
            error = error.value
        )
        if (isLoading.value) AppProgressBar()
    }
}