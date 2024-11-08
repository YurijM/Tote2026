package com.mu.tote2026.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    background = Color1,
    onBackground = Color5,
    surface = Color0,
    onSurface = Color9,
    surfaceVariant = Color2,
    primary = Color7,
    primaryContainer = Color1,
    onPrimary = Color0,
    onPrimaryContainer = Color7,
    outline = Color7
)
private val DarkColorScheme = darkColorScheme(
    background = Color8,
    surface = Color9,
    onSurface = Color0,
    surfaceVariant = Color7,
    primary = Color3,
    primaryContainer = Color8,
    onPrimary = Color9,
    onPrimaryContainer = Color2,
    outline = Color3

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