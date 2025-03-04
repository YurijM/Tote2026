package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.ui.theme.ColorDown
import com.mu.tote2026.ui.theme.ColorDraw
import com.mu.tote2026.ui.theme.ColorUp
import kotlin.math.round

@Composable
fun WinnersPanel(
    winners: List<GamblerModel>
) {
    Text(
        text = "Победители тотализатора",
        fontWeight = FontWeight.Bold,
        color = ColorUp
    )
    LazyRow(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        items(winners) { winner ->
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.winner_card_size))
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = winner.nickname,
                        color = when (winner.place) {
                            1 -> ColorUp
                            2 -> ColorDraw
                            else -> ColorDown
                        },
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            )
                    )
                    SubcomposeAsyncImage(
                        model = winner.photoUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier.size(48.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    strokeWidth = 2.dp
                                )
                            }
                        },
                        modifier = Modifier
                            .requiredSize(dimensionResource(id = R.dimen.winner_photo_size))
                            .clip(RoundedCornerShape(8.dp))
                    )
                    Text(
                        text = "${round(winner.cashPrize).toInt()} руб.",
                        color = when (winner.place) {
                            1 -> ColorUp
                            2 -> ColorDraw
                            else -> ColorDown
                        },
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp,
                                vertical = 4.dp,
                            )
                    )
                }
            }
        }
    }
}
