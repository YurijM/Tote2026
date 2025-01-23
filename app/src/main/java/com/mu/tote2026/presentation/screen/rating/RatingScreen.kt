package com.mu.tote2026.presentation.screen.rating

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.presentation.components.AppProgressBar
import com.mu.tote2026.presentation.navigation.Destinations.GamblerPhotoDestination
import com.mu.tote2026.presentation.utils.errorTranslate
import com.mu.tote2026.presentation.utils.toLog
import com.mu.tote2026.ui.common.UiState
import com.mu.tote2026.ui.theme.ColorDown
import com.mu.tote2026.ui.theme.ColorFemale
import com.mu.tote2026.ui.theme.ColorMale
import com.mu.tote2026.ui.theme.ColorUp

@Composable
fun RatingScreen(
    toGamblerPhoto: (GamblerPhotoDestination) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }

    val viewModel: RatingViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val result = state.result
    var gamblers by remember { mutableStateOf<List<GamblerModel>>(listOf()) }

    LaunchedEffect(key1 = result) {
        toLog("RatingScreen result: $result")
        when (result) {
            is UiState.Loading -> {
                isLoading = true
            }

            is UiState.Success -> {
                isLoading = false
                gamblers = result.data
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
        if (viewModel.rateIsAbsent)
            RateIsAbsent()

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Gender(
                    femalePoints = 123.45,
                    malePoints = 119.87
                )
            }
            items(gamblers) { gambler ->
                RatingItemScreen(
                    gambler,
                    toGamblerPhoto = { toGamblerPhoto(GamblerPhotoDestination(gambler.photoUrl)) },
                )
            }
        }
    }

    if (isLoading) {
        AppProgressBar()
    }

    if (error.isNotBlank()) {
        val context = LocalContext.current
        Toast.makeText(context, errorTranslate(error), Toast.LENGTH_LONG).show()
    }
}

@Composable
fun Gender(
    femalePoints: Double,
    malePoints: Double
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            AssistChip(
                border = BorderStroke(width = 1.dp, color = ColorUp),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = ColorFemale
                ),

                label = {
                    Text(
                        text = femalePoints.toString(),
                        fontWeight = FontWeight.Bold,
                        color = ColorUp
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_female),
                        contentDescription = null,
                        tint = ColorUp
                    )
                },
                onClick = {}
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            AssistChip(
                border = BorderStroke(width = 1.dp, color = ColorDown),
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = ColorMale
                ),

                label = {
                    Text(
                        text = malePoints.toString(),
                        fontWeight = FontWeight.Bold,
                        color = ColorDown
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_male),
                        contentDescription = null,
                        tint = ColorDown
                    )
                },
                onClick = {}
            )
        }
    }
}

@Composable
private fun RateIsAbsent() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colorScheme.error
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            text = "Так как Вы ещё не перечислили свою ставку, " +
                    "то Вам пока доступен только просмотр списка " +
                    "уже зарегистрированных участников",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}