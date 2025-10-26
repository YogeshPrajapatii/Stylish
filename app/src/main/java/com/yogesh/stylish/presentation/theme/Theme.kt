package com.yogesh.stylish.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(primary = PrimaryRed,
    onPrimary = TextWhite,
    background = BackgroundWhite,
    onBackground = TextPrimary,
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    onSurfaceVariant = TextSecondary,
    primaryContainer = White,
    error = ErrorRed)

private val DarkColorScheme = darkColorScheme(primary = PrimaryRed,
    onPrimary = TextWhite,
    background = PrimaryBlack,
    onBackground = TextWhite,
    surface = Color(0xFF1E1E1E),
    onSurface = TextWhite,
    onSurfaceVariant = TextSecondary,
    error = ErrorRed)

@Composable
fun MyFirstComposeAppTheme(darkTheme: Boolean = isSystemInDarkTheme(),
                           content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}