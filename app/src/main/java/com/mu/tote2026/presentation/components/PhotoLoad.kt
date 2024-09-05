package com.mu.tote2026.presentation.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mu.tote2026.R
import com.mu.tote2026.presentation.utils.toLog

@Composable
fun PhotoLoad(
    modifier: Modifier = Modifier,
    photoUrl: String = "",
    onSelect: (uri: Uri) -> Unit,
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        toLog("uri: $uri")
        if (uri != null) {
            imageUri = uri
            onSelect(imageUri!!)
        }
    }

    imageUri?.let { uri ->
        bitmap.value = getBitmap(context, uri)
    }

    Column(
        modifier = modifier.padding(top = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (bitmap.value != null) {
            Image(
                bitmap = bitmap.value!!.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .requiredSize(dimensionResource(id = R.dimen.profile_photo_size))
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            if (photoUrl.isNotBlank()) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .requiredSize(dimensionResource(id = R.dimen.profile_photo_size))
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    /*loading = {
                        Box(
                            modifier = Modifier.size(48.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp
                            )
                        }
                    },*/
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.noname),
                    contentDescription = "gambler",
                    modifier = Modifier.width(dimensionResource(id = R.dimen.profile_photo_size)),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
        Spacer(modifier = Modifier.requiredSize(12.dp))
        Text(
            text = stringResource(id = (if (photoUrl.isBlank()) R.string.load_photo else R.string.change_photo)),
            modifier = Modifier.clickable {
                launcher.launch(
                    PickVisualMediaRequest(
                        mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            },
            textDecoration = TextDecoration.Underline,
        )
    }
}

/*fun PhotoLoading() {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri == null) return@rememberLauncherForActivityResult


    }
}*/

/*private fun bitmapToByteArray(
    context: Context,
    uri: Uri
): ByteArray {
    val inputStream = context.contentResolver.openInputStream(uri)
    val bitmap = BitmapFactory.decodeStream(inputStream)
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
    return baos.toByteArray()
}*/

private fun getBitmap(
    context: Context,
    uri: Uri
): Bitmap {
  val inputStream = context.contentResolver.openInputStream(uri)
  return BitmapFactory.decodeStream(inputStream)
}