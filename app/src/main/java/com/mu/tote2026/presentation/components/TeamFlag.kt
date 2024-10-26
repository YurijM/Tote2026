package com.mu.tote2026.presentation.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import com.mu.tote2026.R
import com.mu.tote2026.ui.theme.color2

@Composable
fun TeamFlag(
    flag: String,
    size: Dp = dimensionResource(id = R.dimen.flag_size)
) {
    val placeholder = rememberVectorPainter(
        image = Icons.Rounded.LocationOn
    )
    var loading by remember { mutableStateOf(true) }

    AsyncImage(
        model = flag,
        placeholder = placeholder,
        contentDescription = "Team Photo",
        contentScale = ContentScale.Crop,
        onSuccess = { loading = false },
        colorFilter = if (loading) ColorFilter.tint(color2) else null,
        modifier = Modifier
            .size(size)
            .aspectRatio(1f / 1f)
            .clip(CircleShape)
    )
}