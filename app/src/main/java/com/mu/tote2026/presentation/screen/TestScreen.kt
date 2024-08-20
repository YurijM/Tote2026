package com.mu.tote2026.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.annotations.concurrent.Background

@Composable
@Preview(showBackground = true)
fun TestScreenPreview() {
    TestScreen()
}

class TestColor(
    val title: String = "",
    val color: Color = Color.Unspecified,
    val background: Color = Color.Unspecified
)

@Composable
fun TestScreen() {
    val testColors = listOf(
        TestColor(
            "onBackground -> background",
            MaterialTheme.colorScheme.onBackground,
            MaterialTheme.colorScheme.background
        ),
        TestColor(
            "onPrimary -> primary",
            MaterialTheme.colorScheme.onPrimary,
            MaterialTheme.colorScheme.primary
        ),
        TestColor(
            "onPrimaryContainer -> onPrimaryContainer",
            MaterialTheme.colorScheme.onPrimaryContainer,
            MaterialTheme.colorScheme.primaryContainer
        ),
        TestColor(
            "onSecondary -> secondary",
            MaterialTheme.colorScheme.onSecondary,
            MaterialTheme.colorScheme.secondary
        ),
        TestColor(
            "onSecondaryContainer -> secondaryContainer",
            MaterialTheme.colorScheme.onSecondaryContainer,
            MaterialTheme.colorScheme.secondaryContainer
        ),
        TestColor(
            "onTertiary -> tertiary",
            MaterialTheme.colorScheme.onTertiary,
            MaterialTheme.colorScheme.tertiary
        ),
        TestColor(
            "onTertiaryContainer -> tertiaryContainer",
            MaterialTheme.colorScheme.onTertiaryContainer,
            MaterialTheme.colorScheme.tertiaryContainer
        ),
        TestColor(
            "onSurface -> surface",
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.colorScheme.surface
        ),
        TestColor(
            "onSurface -> surfaceContainer",
            MaterialTheme.colorScheme.onSurface,
            MaterialTheme.colorScheme.surfaceContainer
        ),
        TestColor(
            "onSurfaceVariant -> surfaceVariant",
            MaterialTheme.colorScheme.onSurfaceVariant,
            MaterialTheme.colorScheme.surfaceVariant
        ),
        TestColor(
            "onError -> error",
            MaterialTheme.colorScheme.onError,
            MaterialTheme.colorScheme.error
        ),
        TestColor(
            "onErrorContainer -> errorContainer",
            MaterialTheme.colorScheme.onErrorContainer,
            MaterialTheme.colorScheme.errorContainer
        ),
    )

    LazyColumn(
        modifier = Modifier.background(Color(0xFF00B4D8))
    ) {
        items(testColors) { test ->
            Text(
                text = test.title,
                color = test.color,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(test.background)
                    .padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}