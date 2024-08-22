package com.mu.tote2026.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.mu.tote2026.R
import com.mu.tote2026.ui.theme.Tote2026Theme

/*@Preview(
    name = "Dark",
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES
)*/

@Preview(
    name = "Light",
    showBackground = true
)
@Composable
fun PreviewLogonButton() {
    Tote2026Theme {
        LogonButton(
            titleId = R.string.sign_up,
            onClick = {}
        )
    }
}

@Composable
fun LogonButton(
   @StringRes titleId: Int,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = { onClick() },
        contentPadding = PaddingValues(
            top = 2.dp,
            start = 12.dp,
            end = 12.dp,
            bottom = 4.dp
        )
    ) {
        Text(
            text = stringResource(titleId),
            style = MaterialTheme.typography.titleLarge.copy(
                letterSpacing = .2.em,
                shadow = Shadow(
                    offset = Offset(2f, 2f),
                    blurRadius = 0f
                )
            )
        )
    }
}