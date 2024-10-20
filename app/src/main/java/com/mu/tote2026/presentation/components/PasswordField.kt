package com.mu.tote2026.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults.contentPaddingWithLabel
import androidx.compose.material3.TextFieldDefaults.contentPaddingWithoutLabel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.mu.tote2026.R

/*@Preview(
    name = "Light",
    showBackground = true
)
@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewPasswordField() {
    Tote2024Theme {
        PasswordTextField(
            label = "password",
            value = "",
            onChange = {},
            painterId = R.drawable.ic_mail,
            description = "email",
            error = stringResource(R.string.error_field_empty)
        )
    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    value: String = "",
    onChange: (newValue: String) -> Unit,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    height: Dp = with(LocalDensity.current) {
        MaterialTheme.typography.displayLarge.lineHeight.toDp()
    },
    textAlign: TextAlign = TextAlign.Unspecified,
    label: String,
    @DrawableRes painterId: Int,
    description: String,
    enabled: Boolean = true,
    error: String = "",
    singleLine: Boolean = true,
    shape: Shape = ShapeDefaults.Small,
) {
    var showPassword by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val visualTransformation = if (showPassword) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }

    Column(
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange = { onChange(it) },
            interactionSource = interactionSource,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(
                fontSize = fontSize,
                textAlign = textAlign
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .padding(8.dp)
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                label = {
                    if (label.isNotBlank()) {
                        Text(label)
                    }
                },
                innerTextField = innerTextField,
                enabled = true,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                leadingIcon = {
                    Icon(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = painterId),
                        contentDescription = description
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                showPassword = !showPassword
                            },
                        painter = if (!showPassword) {
                            painterResource(id = R.drawable.ic_hide)
                        } else {
                            painterResource(id = R.drawable.ic_show)
                        },
                        contentDescription = "eye"
                    )
                },
                contentPadding = if (label.isBlank())
                    contentPaddingWithoutLabel(
                        top = 0.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 0.dp
                    )
                else
                    contentPaddingWithLabel(
                        top = 0.dp,
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 0.dp
                    ),
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = enabled,
                        isError = error.isNotBlank(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
                            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
                            errorLeadingIconColor = MaterialTheme.colorScheme.error,
                        ),
                        interactionSource = interactionSource,
                        shape = shape,
                    )
                }

            )
        }
        if (error.isNotBlank()) {
            TextError(error)
        }
    }
}
