package com.mu.tote2026.presentation.screen.game.list

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2024.presentation.components.AppFabAdd
import com.mu.tote2026.data.repository.GAMBLER
import com.mu.tote2026.domain.model.GameModel
import com.mu.tote2026.domain.model.GroupTeamResultModel
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.components.GameItem
import com.mu.tote2026.presentation.navigation.Destinations.GameDestination
import com.mu.tote2026.presentation.navigation.Destinations.GroupGamesDestination
import com.mu.tote2026.presentation.utils.BRUSH
import com.mu.tote2026.presentation.utils.GROUPS
import com.mu.tote2026.presentation.utils.GROUPS_COUNT
import com.mu.tote2026.presentation.utils.NEW_DOC
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.getGroupTeamResult
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import com.mu.tote2026.ui.theme.ColorDefeat
import com.mu.tote2026.ui.theme.ColorDown
import com.mu.tote2026.ui.theme.ColorDraw
import com.mu.tote2026.ui.theme.ColorFine
import com.mu.tote2026.ui.theme.ColorUp
import com.mu.tote2026.ui.theme.ColorWin

@SuppressLint("MutableCollectionMutableState")
@Composable
fun GameListScreen(
    toGroupGameList: (GroupGamesDestination) -> Unit,
    toGameEdit: (GameDestination) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: GameListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result
    //var groupResult by remember { mutableStateOf<Map<String, List<GroupTeamResultModel>>>(mapOf()) }
    var games by remember { mutableStateOf<List<GameModel>>(listOf()) }

    LaunchedEffect(key1 = result) {
        toLog("GameListScreen $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
                games = result.data
            }

            is UiState.Error -> {
                isLoading = false
                error = result.error
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            var newGroup = false
            GROUPS.takeLast(GROUPS.size - GROUPS_COUNT).reversed().forEach { group ->
                if (games.any { it.group == group }) {
                    newGroup = true
                    item {
                        Text(
                            text = group,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                //items(games.filter { it.group == group }.sortedByDescending { it.id.toInt() }) { game ->
                items(games.filter { it.group == group }.sortedBy { it.id.toInt() }) { game ->
                    GameItem(game) { toGameEdit(GameDestination(game.id)) }
                }
                if (newGroup) {
                    newGroup = false
                    item {
                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }
                }
            }
            items(GROUPS.take(GROUPS_COUNT)) { group ->
                Text(
                    text = "Группа $group",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Games_Table(
                    result = getGroupTeamResult(games.filter { it.group == group }),
                    onClick = { if (GAMBLER.isAdmin) toGroupGameList(GroupGamesDestination(group)) }
                )
            }
        }
    }
    if (GAMBLER.isAdmin) {
        AppFabAdd(
            onAdd = { toGameEdit(GameDestination(NEW_DOC)) }
        )
    }

    if (isLoading) {
        AppProgressBar()
    }

    if (error.isNotBlank()) {
        val context = LocalContext.current
        Toast.makeText(context, errorTranslate(error), Toast.LENGTH_LONG).show()
    }
}

/**
 * The horizontally scrollable table with header and content.
 * @param columnCount the count of columns in the table
 * @param cellWidth the width of column, can be configured based on index of the column.
 * @param data the data to populate table.
 * @param modifier the modifier to apply to this layout node.
 * @param headerCellContent a block which describes the header cell content.
 * @param cellContent a block which describes the cell content.
 */
@Composable
fun <T> Table(
    columnCount: Int,
    cellWidth: (index: Int) -> Dp,
    data: List<T>,
    modifier: Modifier = Modifier,
    headerCellContent: @Composable (index: Int) -> Unit,
    cellContent: @Composable (index: Int, item: T) -> Unit,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .clickable(
                //role = Role.Button,
                onClick = { onClick() }
            ),
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            items((0 until columnCount).toList()) { columnIndex ->
                Column {
                    (0..data.size).forEach { index ->
                        Surface(
                            border = BorderStroke(Dp.Hairline, MaterialTheme.colorScheme.primary),
                            contentColor = Color.Transparent,
                            modifier = Modifier.width(cellWidth(columnIndex))
                        ) {
                            if (index == 0) {
                                headerCellContent(columnIndex)
                            } else {
                                cellContent(columnIndex, data[index - 1])
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Games_Table(
    result: List<GroupTeamResultModel>,
    onClick: () -> Unit
) {
    val cellWidth: (Int) -> Dp = { index ->
        when (index) {
            0 -> 112.dp
            1, 2, 3, 4 -> 36.dp
            5, 6, 7, 10 -> 32.dp
            8 -> 52.dp
            9 -> 36.dp
            else -> 0.dp
        }
    }
    val headerCellTitle: @Composable (Int) -> Unit = { index ->
        val value = when (index) {
            0 -> "Команда"
            1, 2, 3, 4 -> index.toString()
            5 -> "В"
            6 -> "Н"
            7 -> "П"
            8 -> "Мячи"
            9 -> "О"
            10 -> "М"
            else -> ""
        }

        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurface,
            style = TextStyle(fontSize = MaterialTheme.typography.titleSmall.fontSize),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Black
        )
    }

    val cellText: @Composable (Int, GroupTeamResultModel) -> Unit = { index, item ->
        val value = when (index) {
            0 -> item.team
            1 -> item.score1
            2 -> item.score2
            3 -> item.score3
            4 -> item.score4
            5 -> item.win.toString()
            6 -> item.draw.toString()
            7 -> item.defeat.toString()
            8 -> "${item.balls1}:${item.balls2}"
            9 -> item.points.toString()
            10 -> if (item.place > 0) item.place.toString() else ""
            else -> ""
        }

        if (value == BRUSH) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = ""
                )
            }
        } else {
            val isDigit = (value.isEmpty() || value[0].isDigit())
            Text(
                text = value,
                color = when (index) {
                    1 -> getScoreColor(item.score1.split(":"))
                    2 -> getScoreColor(item.score2.split(":"))
                    3 -> getScoreColor(item.score3.split(":"))
                    4 -> getScoreColor(item.score4.split(":"))
                    5 -> ColorWin
                    6 -> ColorDraw
                    7 -> ColorDefeat
                    10 -> when (value) {
                        "1" -> ColorUp
                        "2" -> ColorDown
                        else -> ColorDefeat
                    }

                    else -> MaterialTheme.colorScheme.onSurface
                },
                textAlign = if (isDigit) TextAlign.Center else TextAlign.Start,
                modifier = Modifier.padding(4.dp),
                maxLines = 1,
                overflow = if (isDigit) TextOverflow.Clip else TextOverflow.Ellipsis
            )
        }
    }

    Table(
        columnCount = 11,
        cellWidth = cellWidth,
        data = result,
        modifier = Modifier.padding(bottom = 4.dp),
        headerCellContent = headerCellTitle,
        cellContent = cellText,
        onClick = { onClick() }
    )
}

fun getScoreColor(list: List<String>): Color {
    return if (list.size == 2) {
        when {
            (list[0].toInt() > list[1].toInt()) -> ColorWin
            (list[0].toInt() < list[1].toInt()) -> ColorDefeat
            else -> ColorDraw
        }
    } else ColorFine
}
