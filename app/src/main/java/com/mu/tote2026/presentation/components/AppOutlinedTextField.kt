package com.mu.tote2026.presentation.components

import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onChange: (String) -> Unit,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    height: Dp = with(LocalDensity.current) {
        MaterialTheme.typography.displayLarge.lineHeight.toDp()
    },
    textAlign: TextAlign = TextAlign.Unspecified,
    label: String = "",
    @DrawableRes painterId: Int? = null,
    description: String? = null,
    enabled: Boolean = true,
    error: String = "",
    singleLine: Boolean = true,
    shape: Shape = ShapeDefaults.Small,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
    ) {
        BasicTextField(
            value = value,
            onValueChange = { onChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                //.width(OutlinedTextFieldDefaults.MinWidth)
                .padding(8.dp),
            interactionSource = interactionSource,
            singleLine = singleLine,
            textStyle = TextStyle(
                fontSize = fontSize,
                textAlign = textAlign
            ),
            keyboardOptions = keyboardOptions,
            //textStyle = TextStyle(fontSize = 24.sp),
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
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                leadingIcon = if (painterId != null) {
                    {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(id = painterId),
                            contentDescription = description
                        )
                    }
                } else null,
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
                            errorLeadingIconColor = MaterialTheme.colorScheme.error,
                        ),
                        interactionSource = interactionSource,
                        shape = shape,
                    )
                }
            )
        }
    }

    /*BasicTextField(
        value = value,
        onValueChange = { onChange(it) },
        //modifier = modifier,
// internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine
    ) {
        OutlinedTextFieldDefaults.DecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
// same interaction source as the one passed to BasicTextField to read focus state
// for text field styling
            interactionSource = interactionSource,
            //supportingText = {},
// keep horizontal paddings but change the vertical
            contentPadding = OutlinedTextFieldDefaults.contentPadding(
                top = 8.dp,
                bottom = 8.dp
            ),
// update border colors
            colors = colors,
// update border thickness and shape
            container = {
                OutlinedTextFieldDefaults.Container(
                    enabled = enabled,
                    isError = false,
                    colors = colors,
                    interactionSource = interactionSource,
                    shape = RectangleShape,
                    unfocusedBorderThickness = 2.dp,
                    focusedBorderThickness = 4.dp
                )
            },
        )
    }*/
}