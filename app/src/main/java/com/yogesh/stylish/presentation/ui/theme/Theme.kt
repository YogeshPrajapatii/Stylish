package com.yogesh.stylish.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// This is your custom color scheme using the colors you defined in Color.kt
private val AppColorScheme = lightColorScheme(
    primary = StylishRed,
    onPrimary = White,
    secondary = StylishGray,
    background = StylishBackground,
    surface = White,
    onBackground = StylishBlack,
    onSurface = StylishBlack,
)

@Composable
fun MyFirstComposeAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = AppColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Make the status bar transparent
            window.statusBarColor = Color.Transparent.toArgb()
            // Tell the system whether the status bar icons should be dark or light
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}