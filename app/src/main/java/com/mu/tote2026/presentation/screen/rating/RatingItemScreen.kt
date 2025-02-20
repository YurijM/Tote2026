package com.mu.tote2026.presentation.screen.rating

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.presentation.utils.MALE
import com.mu.tote2026.ui.theme.Color2
import com.mu.tote2026.ui.theme.ColorDown
import com.mu.tote2026.ui.theme.ColorFemale
import com.mu.tote2026.ui.theme.ColorMale
import com.mu.tote2026.ui.theme.ColorUp
import kotlin.math.round

@SuppressLint("DefaultLocale")
@Composable
fun RatingItemScreen(
    gambler: GamblerModel,
    toGamblerPhoto: () -> Unit
) {
    val placeholder = rememberVectorPainter(
        image = Icons.Rounded.AccountCircle
    )
    var loadingPhoto by remember { mutableStateOf(true) }

    val deltaPlace = gambler.placePrev - gambler.place
    var arrow = "  "
    var arrowValue = ""
    var color = MaterialTheme.colorScheme.onSurface

    if (deltaPlace > 0) {
        arrow = "↑"
        arrowValue = "+$deltaPlace"
        color = ColorUp
    } else if (deltaPlace < 0) {
        arrow = "↓"
        arrowValue = "$deltaPlace"
        color = ColorDown
    }

    var colorGender = ColorFemale
    var painterGender = R.drawable.ic_female
    if (gambler.gender == MALE) {
        colorGender = ColorMale
        painterGender = R.drawable.ic_male
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            AsyncImage(
                model = gambler.photoUrl,
                placeholder = placeholder,
                contentDescription = "User Photo",
                contentScale = ContentScale.Crop,
                onSuccess = { loadingPhoto = false },
                colorFilter = if (loadingPhoto) ColorFilter.tint(Color2) else null,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.gambler_photo_size))
                    .aspectRatio(1f / 1f)
                    .clip(CircleShape)
                    .clickable { toGamblerPhoto() }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = painterGender),
                        contentDescription = null,
                        tint = colorGender
                    )
                    Text(
                        gambler.nickname,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.height(20.dp)
                    )
                }
                val cashPrize = buildAnnotatedString {
                    append("Выигрыш - ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Black
                        )
                    ) {
                        append(round(gambler.cashPrize).toInt().toString())
                    }
                    append(" руб.")
                }
                Text(
                    text = cashPrize,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize
                )
            }
            if (gambler.place != 0 && gambler.placePrev != 0) {
                Text(
                    text = arrow,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = color,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = arrowValue,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    color = color,
                    modifier = Modifier
                        .weight(.1f)
                )
                /*Text(
                    text = gambler.points.toString(),
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .weight(.2f)
                )*/
            }
            Text(
                text = String.format("%.2f", gambler.points),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(.25f)
            )
        }
    }
}