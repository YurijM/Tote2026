package com.mu.tote2026.presentation.screen.auth.signIn

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.ui.common.UiState

@Composable
fun SignInScreen(
    viewModel:SignInViewModel = hiltViewModel()
) {
    val isLoading = remember { mutableStateOf(false) }
    val state by viewModel.state.collectAsState()

    when (state.result) {
        is UiState.Loading -> {
            isLoading.value = true
        }

        is UiState.Success -> {
            isLoading.value = false
        }

        is UiState.Error -> {}

        else -> {}
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { newValue ->
                viewModel.onEvent(SignInEvent.OnEmailChange(newValue))
            },
            singleLine = true,
            label = { Text(text = "email") },
            placeholder = { Text(text = "Введите адрес email") },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_email),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { newValue ->
                viewModel.onEvent(SignInEvent.OnPasswordChange(newValue))
            },
            singleLine = true,
            label = { Text(text = "Пароль") },
            placeholder = { Text(text = "Введите пароль") },
            leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_password),
                    contentDescription = null
                )
            },
            trailingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_eye),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { viewModel.onEvent(SignInEvent.OnSignIn) }
        ) {
            Text(text = "Авторизоваться")
        }
    }
    /*if (isLoading.value) {
        AppProgressBar()
    }*/
}