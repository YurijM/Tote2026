package com.mu.tote2026.presentation.screen.rating

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.mu.tote2026.ui.theme.Color2

@Composable
fun GamblerPhotoScreen(
    photoUrl: String
) {
    val placeholder = rememberVectorPainter(
        image = Icons.Rounded.AccountCircle
    )
    var loadingPhoto by remember { mutableStateOf(true) }

    AsyncImage(
        model = photoUrl,
        placeholder = placeholder,
        contentDescription = "User Photo",
        contentScale = if (loadingPhoto) ContentScale.Fit else ContentScale.Crop,
        onSuccess = { loadingPhoto = false },
        colorFilter = if (loadingPhoto) ColorFilter.tint(Color2) else null,
    )
}