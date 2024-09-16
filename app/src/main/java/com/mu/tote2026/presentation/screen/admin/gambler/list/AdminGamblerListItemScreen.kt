package com.mu.tote2026.presentation.screen.admin.gambler.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminGamblerListItemScreen(
    nickname: String,
    onEdit: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(.5f)
            .padding(vertical = 8.dp),
    ) {
        Text(
            text = nickname,
            modifier = Modifier.weight(5f),
        )
        IconButton(
            onClick = { onEdit() },
            modifier = Modifier.weight(1f).size(24.dp),
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "editEmail"
            )
        }
    }
}