package com.yogesh.stylish.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light Color Scheme (Din ke mode ke liye)
private val LightColorScheme = lightColorScheme(
    primary = StylishRed,
    onPrimary = White,
    primaryContainer = Color(0xFFFFEBEE), // Laal ka halka shade
    onPrimaryContainer = StylishRed,
    secondary = StylishGray,
    onSecondary = White,
    background = StylishBackground,
    onBackground = StylishBlack,
    surface = White,
    onSurface = StylishBlack,
    onSurfaceVariant = StylishGray // Secondary text/icons ke liye
)

// Dark Color Scheme (Raat ke mode ke liye - abhi ke liye basic)
private val DarkColorScheme = darkColorScheme(
    primary = StylishRed,
    onPrimary = White,
    primaryContainer = Color(0xFFB71C1C),
    onPrimaryContainer = White,
    background = StylishBlack,
    surface = Color(0xFF121212),
    onBackground = White,
    onSurface = White
)

@Composable
fun MyFirstComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Sahi color scheme chunein
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}