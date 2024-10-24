package com.mu.tote2026.presentation.screen.admin.gambler.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mu.tote2026.R
import com.mu.tote2026.domain.model.GamblerModel
import com.mu.tote2026.ui.theme.Color2

@Composable
fun AdminGamblerListItemScreen(
    gambler: GamblerModel,
    onEdit: () -> Unit
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (gambler.isAdmin) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier
            .fillMaxWidth(.75f)
            .padding(vertical = 8.dp)
            .clickable { onEdit() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            GamblerPhoto(gambler.photoUrl)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            ) {
                Text(
                    text = gambler.nickname,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.height(20.dp)
                )
                Text(
                    text = gambler.email,
                    fontSize = MaterialTheme.typography.labelLarge.fontSize,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.Bold)
                    ) {
                        append("${gambler.rate}")
                    }
                    append(" руб.")
                }
            )
        }
    }
}

@Composable
private fun GamblerPhoto(
    photoUrl: String
) {
    val placeholder = rememberVectorPainter(
        image = Icons.Rounded.AccountCircle
    )
    var loading by remember { mutableStateOf(true) }

    AsyncImage(
        model = photoUrl,
        placeholder = placeholder,
        contentDescription = "Gambler Photo",
        contentScale = ContentScale.Crop,
        onSuccess = { loading = false },
        colorFilter = if (loading) ColorFilter.tint(Color2) else null,
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.gambler_photo_size))
            .aspectRatio(1f / 1f)
            .clip(CircleShape)
    )
}