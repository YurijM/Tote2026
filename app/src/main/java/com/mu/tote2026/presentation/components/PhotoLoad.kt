package com.mu.tote2026.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import com.mu.tote2026.R
import com.mu.tote2026.ui.theme.Color2

@Composable
fun PhotoLoad(
    modifier: Modifier = Modifier,
    photoUrl: String = "",
    onSelect: (uri: Uri) -> Unit,
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            imageUri = uri
            onSelect(imageUri!!)
        }
    }

    Column(
        modifier = modifier.padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (imageUri != null) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUri),
                contentDescription = null,
                modifier = Modifier
                    .requiredSize(dimensionResource(id = R.dimen.profile_photo_size))
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            if (photoUrl.isNotBlank()) {
                SubcomposeAsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(dimensionResource(id = R.dimen.profile_photo_size))
                        .clip(RoundedCornerShape(8.dp)),
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
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.noname),
                    contentDescription = "gambler",
                    modifier = Modifier.width(dimensionResource(id = R.dimen.profile_photo_size)),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color2)
                )
            }
        }
        Spacer(modifier = Modifier.requiredSize(12.dp))
        Text(
            text = stringResource(id = (if (photoUrl.isBlank()) R.string.load_photo else R.string.change_photo)),
            modifier = Modifier.clickable {
                launcher.launch("image/*")
            },
            textDecoration = TextDecoration.Underline,
        )
    }
}
