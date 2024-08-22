package com.mu.tote2026.presentation.screen.auth.signIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel()
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
            //.padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp)
                    .weight(1f)
            ) {
                Title(title = stringResource(id = R.string.sign_in))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    /*border = BorderStroke(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline
                    ),*/
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 20.dp
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            value = viewModel.email,
                            onChange = { newValue ->
                                viewModel.onEvent(SignInEvent.OnEmailChange(newValue))
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
                                viewModel.onEvent(SignInEvent.OnPasswordChange(newValue))
                            },
                            painterId = R.drawable.ic_password,
                            description = "password",
                            errorMessage = viewModel.errorPassword
                        )
                        OkAndCancel(
                            titleOk = stringResource(id = R.string.to_log_into),
                            enabledOk = viewModel.enabledButton,
                            showCancel = false,
                            onOK = {
                                viewModel.onEvent(SignInEvent.OnSignIn)
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
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    //if (isLoading.value) AppProgressBar()
                    AppProgressBar()
                }
                Image(
                    painter = painterResource(id = R.drawable.field1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    /*Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
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
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLeadingIconColor = MaterialTheme.colorScheme.primary
                ),
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
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            Box(modifier = Modifier.weight(1f)) {
                //if (isLoading.value) AppProgressBar()
                AppProgressBar()
            }
            Image(
                painter = painterResource(id = R.drawable.field1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }*/

    /*if (error.value.isNotBlank()) {
        Toast.makeText(context, error.value, Toast.LENGTH_LONG).show()
        error.value = ""
    }*/
}
