package com.mu.tote2026.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    @StringRes titleId: Int,
    @StringRes titleOkId: Int,
    email: String,
    onEmailChange: (String) -> Unit,
    errorEmail: String?,
    password: String,
    onPasswordChange: (String) -> Unit,
    errorPassword: String?,
    isRegistration: Boolean = false,
    passwordConfirm: String = "",
    onPasswordConfirmChange: ((String) -> Unit)? = null,
    errorPasswordConfirm: String? = null,
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
        ) {
            Title(titleId = titleId)
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
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
                        value = email,
                        onChange = { newValue ->
                            onEmailChange(newValue)
                        },
                        label = stringResource(id = R.string.enter_email),
                        painterId = R.drawable.ic_email,
                        description = "email",
                        errorMessage = errorEmail,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email,
                        )
                    )
                    PasswordTextField(
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
                        errorMessage = errorPassword
                    )
                    if (isRegistration) {
                        PasswordTextField(
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
                            errorMessage = errorPasswordConfirm
                        )
                    }
                    OkAndCancel(
                        titleOk = titleOkId,
                        enabledOk = enabledButton,
                        showCancel = false,
                        onOK = { onSign() },
                        onCancel = {}
                    )
                    if (error.isNotBlank()) {
                        TextError(
                            errorMessage = error,
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
                painter = painterResource(id = R.drawable.field),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}