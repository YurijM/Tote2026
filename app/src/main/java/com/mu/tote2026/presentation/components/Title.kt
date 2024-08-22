package com.mu.tote2026.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/*@Preview(
    name = "Light",
    showBackground = true
)
@Composable
fun PreviewTitle() {
    Tote2026Theme {
        Title(
            titleId = R.string.sign_in
        )
    }
}*/

@Composable
fun Title(
    @StringRes titleId: Int,
    color: Color = MaterialTheme.colorScheme.primary
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        text = stringResource(id = titleId),
        color = color,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge
    )
}