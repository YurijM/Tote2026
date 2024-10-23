package com.mu.tote2026.presentation.screen.rating

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.ui.theme.Color2

@Composable
fun RatingItemScreen(
    gambler: GamblerModel
) {
    val placeholder = rememberVectorPainter(
        image = Icons.Rounded.AccountCircle
    )
    var loadingPhoto by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            )
    ) {
        AsyncImage(
            model = gambler.photoUrl,
            placeholder = placeholder,
            contentDescription = "User Photo",
            contentScale = ContentScale.Crop,
            onSuccess = { loadingPhoto = false },
            colorFilter = if (loadingPhoto) ColorFilter.tint(Color2) else null,
            modifier = Modifier
                .size(36.dp)
                .aspectRatio(1f / 1f)
                .clip(CircleShape)
        )
        Text(
            gambler.nickname,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 4.dp)
        )
        Text(
            gambler.points.toString(),
            fontSize = 20.sp,
        )
    }
}