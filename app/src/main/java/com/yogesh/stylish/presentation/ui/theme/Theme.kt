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

private val LightColorScheme = lightColorScheme(
    primary = StylishRed,
    onPrimary = White,
    primaryContainer = Color(0xFFFFEBEE), 
    onPrimaryContainer = StylishRed,
    secondary = StylishGray,
    onSecondary = White,
    background = StylishBackground,
    onBackground = StylishBlack,
    surface = White,
    onSurface = StylishBlack,
    onSurfaceVariant = StylishGray,
    
    )

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
    var colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    colorScheme = colorScheme.copy(surfaceTint = Color.Transparent)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}