package com.yogesh.stylish.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Light Color Scheme based on Figma
private val LightColorScheme = lightColorScheme(
    primary = StylishRed,
    onPrimary = White,
    secondary = StylishBlack,
    onSecondary = White,
    error = ErrorRed,
    onError = White,
    background = BackgroundGrey,
    onBackground = TextBlack,
    surface = White,
    onSurface = TextBlack,
    surfaceVariant = BackgroundGrey,
    onSurfaceVariant = TextGrey,
    outline = DividerGrey
)

// Dark Color Scheme - Can be refined later
private val DarkColorScheme = darkColorScheme(
    primary = StylishRed,
    onPrimary = White,
    secondary = TextGrey,
    onSecondary = TextBlack,
    error = ErrorRed,
    onError = White,
    background = Color(0xFF121212),
    onBackground = White,
    surface = Color(0xFF1E1E1E),
    onSurface = White,
    surfaceVariant = Color(0xFF2A2A2A),
    onSurfaceVariant = TextGrey,
    outline = Color(0xFF444444)
)

@Composable
fun StylishTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val baseColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Override surface tint to keep cards white/dark on elevation
    val finalColorScheme = baseColorScheme.copy(
        surfaceTint = if (darkTheme) baseColorScheme.surface else White
    )

    MaterialTheme(
        colorScheme = finalColorScheme,
        typography = Typography,
        content = content
    )
}
