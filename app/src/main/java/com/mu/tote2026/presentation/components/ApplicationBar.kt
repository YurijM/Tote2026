package com.mu.tote2026.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.mu.tote2026.R
import com.mu.tote2026.ui.theme.Color2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationBar(
    photoUrl: String,
    isAdmin: Boolean,
    onImageClick: () -> Unit,
    onAdminClick: () -> Unit,
    onSignOut: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                actionIconContentColor = MaterialTheme.colorScheme.onSurface
            ),
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            actions = {
                if (photoUrl.isNotBlank()) {
                    val placeholder = rememberVectorPainter(
                        image = Icons.Rounded.AccountCircle
                    )
                    var loading by remember { mutableStateOf(true) }

                    AsyncImage(
                        model = photoUrl,
                        placeholder = placeholder,
                        contentDescription = "User Photo",
                        contentScale = ContentScale.Crop,
                        onSuccess = { loading = false },
                        colorFilter = if (loading) ColorFilter.tint(Color2) else null,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.app_bar_photo_size))
                            .aspectRatio(1f / 1f)
                            .clip(CircleShape)
                            .clickable {
                                onImageClick()
                            }
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.noname),
                        contentDescription = "gambler",
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.app_bar_no_name_size))
                            .clip(CircleShape)
                            .clickable { onImageClick() },
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                    )
                }
                if (isAdmin) {
                    IconButton(
                        onClick = onAdminClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "settings"
                        )
                    }
                }
                IconButton(
                    onClick = {
                        onSignOut()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "exit"
                    )
                }
            }
        )
    }
}