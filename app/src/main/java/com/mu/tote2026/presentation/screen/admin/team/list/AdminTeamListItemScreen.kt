package com.mu.tote2026.presentation.screen.admin.team.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mu.tote2026.domain.model.TeamModel
import com.mu.tote2026.presentation.components.TeamFlag

@Composable
fun AdminTeamListItemScreen(
    team: TeamModel
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
            .padding(top = 4.dp, bottom = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            TeamFlag(team.flag)
            Text(
                text = team.team,
                modifier = Modifier.padding(start = 4.dp)
            )
            Text(
                text = "(группа ${team.group})",
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
