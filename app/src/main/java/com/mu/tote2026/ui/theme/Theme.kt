package com.mu.tote2026.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    background = color1,
    onBackground = color5,
    surface = color0,
    onSurface = color9,
    surfaceVariant = color2,
    primary = color7,
    primaryContainer = color1,
    onPrimary = color0,
    onPrimaryContainer = color7,
    outline = color7
)
private val DarkColorScheme = darkColorScheme(
    background = color8,
    surface = color9,
    onSurface = color0,
    surfaceVariant = color7,
    primary = color3,
    primaryContainer = color8,
    onPrimary = color9,
    onPrimaryContainer = color2,
    outline = color3

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun Tote2026Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    /*val colorScheme = when {
        *//*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*//*

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }*/
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}