package com.mu.tote2026.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mu.tote2026.R

@Composable
fun SignCard(
    title: String,
    titleOk: String,
    email: String,
    onEmailChange: (String) -> Unit,
    errorEmail: String?,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorPassword: String = "",
    isRegistration: Boolean = false,
    passwordConfirm: String = "",
    onPasswordConfirmChange: ((String) -> Unit)? = null,
    errorPasswordConfirm: String = "",
    enabledButton: Boolean,
    onSign: () -> Unit,
    error: String
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
                /*.padding(
                    top = 0.dp,
                    start = 48.dp,
                    end = 48.dp,
                    bottom = 48.dp
                )*/
                .verticalScroll(rememberScrollState())
        ) {
            Title(title = title)
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp,
                            start = 12.dp,
                            end = 12.dp,
                            bottom = 16.dp,
                        )
                ) {
                    AppOutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        value = email,
                        onChange = { newValue ->
                            onEmailChange(newValue)
                        },
                        label = stringResource(id = R.string.enter_email),
                        painterId = R.drawable.ic_email,
                        description = "email",
                        error = errorEmail ?: "",
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                        )
                    )
                    PasswordField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        label = stringResource(id = R.string.enter_password),
                        value = password,
                        onChange = { newValue ->
                            onPasswordChange(newValue)
                        },
                        painterId = R.drawable.ic_password,
                        description = "password",
                        error = errorPassword
                    )
                    if (isRegistration) {
                        PasswordField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                            label = stringResource(id = R.string.confirm_password),
                            value = passwordConfirm,
                            onChange = { newValue ->
                                if (onPasswordConfirmChange != null) {
                                    onPasswordConfirmChange(newValue)
                                }
                            },
                            painterId = R.drawable.ic_password,
                            description = "password confirm",
                            error = errorPasswordConfirm
                        )
                    }
                    OkAndCancel(
                        titleOk = titleOk,
                        enabledOk = enabledButton,
                        showCancel = false,
                        onOK = { onSign() },
                        onCancel = {}
                    )
                    if (error.isNotBlank()) {
                        TextError(
                            error = error,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.weight(1f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.field),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}