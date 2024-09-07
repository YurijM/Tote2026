package com.mu.tote2026.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mu.tote2026.R
import com.mu.tote2026.presentation.utils.BottomNavItem
import com.mu.tote2026.presentation.utils.YEAR_START

@Composable
fun BottomNav(
    currentRoute: String?,
    currentYear: Int,
    onNavigate: (String) -> Unit,
) {
    val years = YEAR_START.toString() +
            if (currentYear != YEAR_START) {
                "-$currentYear"
            } else {
                ""
            } + " ©"
    val navItems = listOf(
        BottomNavItem.RatingItem,
        BottomNavItem.StakeItem,
        BottomNavItem.PrognosisItem,
        BottomNavItem.GamesItem,
    )
    Column {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            navItems.forEach { item ->
                val title = stringResource(id = item.titleId)

                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        unselectedTextColor = MaterialTheme.colorScheme.onBackground,
                        unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                        selectedTextColor = MaterialTheme.colorScheme.onSurface,
                        selectedIconColor = MaterialTheme.colorScheme.onSurface
                    ),
                    selected = (item.route == currentRoute),
                    onClick = { onNavigate(item.route) },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconId),
                            contentDescription = title
                        )
                    },
                    label = {
                        Text(text = title)
                    },
                    alwaysShowLabel = true
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.author),
                contentDescription = "author",
                modifier =Modifier.size(28.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = years,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}