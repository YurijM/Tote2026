package com.mu.tote2026.presentation.screen.admin.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mu.tote2026.R
import com.mu.tote2026.presentation.utils.AdminNavItem

@Composable
fun AdminMainItemScreen(
    adminItem: AdminNavItem,
    toItem: (String) -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 4.dp,
                    horizontal = 20.dp
                )
                .clickable {
                    toItem(adminItem.route)
                }
        ) {
            Text(
                text = stringResource(adminItem.titleId),
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_double_arrow),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }

}