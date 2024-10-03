package com.mu.tote2026.presentation.screen.admin.group.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun AdminGroupListItemScreen(
    group: String,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth(.75f)
            .padding(vertical = 8.dp)
            .clickable { onEdit() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            /*Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(.75f)
            .padding(vertical = 8.dp),
    ) {*/
            Text(
                text = group,
                modifier = Modifier.weight(5f),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = { onEdit() },
                modifier = Modifier
                    .weight(1f)
                    .size(24.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "editGroup"
                )
            }
            IconButton(
                onClick = { onDelete() },
                modifier = Modifier
                    .weight(1f)
                    .size(24.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "deleteGroup",
                )
            }
        }
    }
}