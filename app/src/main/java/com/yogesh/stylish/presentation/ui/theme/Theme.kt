package com.yogesh.stylish.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(primary = StylishRed,
    onPrimary = White,
    primaryContainer = Color(0xFFFFEBEE),
    secondary = StylishGray,
    onSecondary = White,
    background = White,
    onBackground = StylishBlack,
    surface = White,
    onSurface = StylishBlack,
    onSurfaceVariant = StylishGray)

private val DarkColorScheme = darkColorScheme(primary = StylishRed,
    onPrimary = White,
    background = StylishBlack,
    onBackground = White,
    surface = Color(0xFF1E1E1E),
    onSurface = White,
    onSurfaceVariant = StylishGray)

@Composable
fun MyFirstComposeAppTheme(darkTheme: Boolean = isSystemInDarkTheme(),
                           content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}